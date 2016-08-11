var Interviews = React.createClass({

getInitialState: function() {
	return {interviews: []};
},
load: function() {
	$.ajax({ url: "/api/interviews", dataType: 'json',
	success: function(value) {
		this.setState({interviews: value});
	}.bind(this),
		error: function(xhr, status, err) {
		console.error("/api/interviews", status, err.toString());
	}.bind(this)
	});
},
componentDidMount: function() {
    this.load();
},
render: function() {

    var that = this;
    var interviews = this.state.interviews.map(function(interview){

        var selectRef = "q_select_" + interview.id;
        var inputRef = "q_intput_" + interview.id;

        return (
            <div className="interview-div" key={interview.id}>
            <h1>{interview.name}</h1>
            <h4>{interview.description}</h4>
            <div>{interview.evaluationDescription}</div>
            <br/>
            Questions:
            <br/><br/>
            <Questions questions={interview.questions} load={that.load}/>
            <Input className="add-question-input" label="Question text:" ref={inputRef} placeholder='Question text' type='text' id={inputRef}/>
            <select className="add-question-select" ref={selectRef}>
                <option value="CHECKBOX">Checkbox</option>
                <option value="TEXT_AREA">Text</option>
                <option value="RADIO">Radio</option>
            </select>
            <Button className="add-question-btn" onClick={that.addQuestion.bind(that, interview)} bsStyle='primary'>Add question</Button>
            </div>
        );
    });

    return (
        <div className="interviews">
            {interviews}
        </div>
    );
},
addQuestion: function(interview) {
    var data = {};
	data.text = this.refs["q_intput_" + interview.id].getValue();
    this.refs["q_intput_" + interview.id].getDOMNode().lastChild.value="";
	var questionSelect = React.findDOMNode(this.refs["q_select_" + interview.id]);
	data.questionType = questionSelect[questionSelect.selectedIndex].value;
	data.position = interview.questions.length + 1;

	$.ajax({ url: "/api/interviews/" + interview.id + "/questions", dataType: 'json', type: 'POST', data : JSON.stringify(data) , headers : {'Accept' : 'application/json', 'Content-Type' : 'application/json'},
		success:    function(interview)         {    this.load(); }.bind(this),
		error:      function(xhr, status, err)  {    console.log(err); }.bind(this)
	});


}

});

var Questions = React.createClass({

getInitialState: function() {
	return {questions: this.props.questions};
},

render: function() {

     var that = this;
     var questions = this.props.questions.map(function(question){
            return(
                <div className="question-div">
                    {question.text}:
                    <br/>
                    <Answers answers={question.answers} questionType={question.questionType} questionId={question.id} load={that.props.load}/>
                    <div>
                        <Button className="delete-question-btn" onClick={that.deleteQuestion.bind(that, question.id)} bsStyle='danger'>Delete question</Button>
                    </div>
                    <br/>
                    <br/>
                </div>
            );
     });

    return <div>{questions}</div>;
},

deleteQuestion: function (questionId) {
    $.ajax({
	    url: "/question/" + questionId,
	    type: 'DELETE',
		success: (
		    function(question) {
		        this.props.load();
		    }.bind(this)
		),
		error: (
		    function(xhr, status, err) {
		        console.log(err);
		    }.bind(this)
		)
	});
}
});


var Answers = React.createClass({

getInitialState: function() {
	return {answers: this.props.answers};
},

render: function() {

    var that = this;

    if( this.props.questionType == 'TEXT_AREA'){
        return (
            <textarea key={this.props.answers[0].id} defaultValue={this.props.answers[0].text} rows="4" cols="50"/>
        );
    }

    var answers = this.props.answers.map(function (answer) {
        return (
            <div className="answer-div">
                <input key={answer.id} type={that.props.questionType} name="answers"/>{answer.text}
            </div>
        )
    });

    var answersControl = this.props.answers.map(function (answer) {
        return (
            <div className="answer-ctrl-div">
                &nbsp;<input type={that.props.questionType} name="answers" checked={answer.correct} onChange={that.changeCorrectness.bind(that, answer.id)}/>
                &nbsp;<a className="answer-delete-btn" onClick={that.deleteAnswer.bind(that, answer.id)}>&#10006;</a>
            </div>
        )
    });

    var answerInputRef = "a_input_" + this.props.questionId;
    return (
        <div>
            <div>
                <fieldset style={{float: 'left'}}>
                    {answers}
                </fieldset>
                <fieldset style={{float: 'left'}}>
                    {answersControl}
                </fieldset>
            </div>
            <Input className="add-answer-input" ref={answerInputRef} placeholder='Answer text' type='text'/>
            <Button className="add-answer-btn" onClick={that.addAnswer.bind(that)} bsStyle='success'>Add answer</Button>
        </div>
    );

},

addAnswer: function() {
    var data = {};
	data.text = this.refs["a_input_" + this.props.questionId].getValue();

	$.ajax({
	    url: "/question/" + this.props.questionId + "/answers",
	    dataType: 'json', type: 'POST',
	    data : JSON.stringify(data) ,
	    headers : {'Accept' : 'application/json', 'Content-Type' : 'application/json'},
		success: (
		    function(answer) {
		        this.props.load();
		    }.bind(this)
		),
		error: (
		    function(xhr, status, err) {
		        console.log(err);
		    }.bind(this)
		)
	});
},

deleteAnswer: function(answerId) {
	$.ajax({
	    url: "/answer/" + answerId,
	    type: 'DELETE',
		success: (
		    function() {
		        this.props.load();
		    }.bind(this)
		),
		error: (
		    function(xhr, status, err) {
		        console.log(err);
		    }.bind(this)
		)
	});
},

changeCorrectness: function(answerId) {
	$.ajax({
	    url: "/answer/" + answerId + "/changeCorrectness",
	    type: 'PUT',
		success: (
		    function(answer) {
		        this.props.load();
		    }.bind(this)
		),
		error: (
		    function(xhr, status, err) {
		        console.log(err);
		    }.bind(this)
		)
	});
}

});