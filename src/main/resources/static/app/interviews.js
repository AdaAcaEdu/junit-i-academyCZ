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

    var interviews = this.state.interviews.map(function(interview){
        return <div key={interview.id}>
               <h1>{interview.name}</h1>
               <h4>{interview.description}</h4>
               <div>{interview.evaluationDescription}</div>
               <br/>
               Questions:
               <br/><br/>
               </div>
        ;
    });
    return (
        <div className="interviews">
            {interviews}
        </div>
    );
}
});