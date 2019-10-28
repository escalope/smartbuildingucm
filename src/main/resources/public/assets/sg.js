//var source = new EventSource("/new_notification");
var charts = {};
var TIME_WINDOW = 10;
var CHART_INDEX=0
var dataPoints = [[]];

// it creates a chart to be inserted in the main page
charts.chart1 = new CanvasJS.Chart("chart1", {
    title: {
        text: "Sensor measurement"
    },
     legend: {
       horizontalAlign: "right", // "center" , "right"
       verticalAlign: "top",  // "top" , "bottom"
       fontSize: 12
     },axisX:{      
				
				title:"Value",
				labelAngle: -50,
				labelFontColor: "rgb(0,75,141)"

			},
	
			axisY: {
				title: "Units",
				interlacedColor: "azure",
				tickColor: "azure",
				titleFontColor: "rgb(0,75,141)",
                                interval: 5
				
				
			},
height: 400,
    data: [

{
            type: "line",showInLegend: true,
      legendText: "Valores",markerType:"circle",markerSize:15,
            dataPoints: dataPoints[CHART_INDEX]
        }
    ]
});

// it connects to a SSE source. Any method in the server side
// returning a SSE emitter
var source = new EventSource("/building/temprt");

// binds the opening of the channel to a console message 
source.addEventListener('open', function (e) {
    console.log("Connection open");
}, false);

// binds the error in opening of the channel to a console message
source.addEventListener('error', function (e) {
    console.log("Connection closed");
}, false);

 /*source.onmessage = function(e) {
    // this code allows to insert the notification code into the page    
    var notification = JSON.parse(e.data);               
    document.getElementById("notificationDiv").innerHTML += notification.value + " at " + new Date(notification.time) + "<br/>";
  };*/
 
 // this code inserts received notification data into a
    // chart. The code is prepared to handle several charts
    // depending on 
 source.onmessage = function(e) {    
    var notification = JSON.parse(e.data);
    dataPoints[CHART_INDEX].push({
            x: new Date(notification.timestamp),
            y: notification.value
        });
        if (dataPoints[CHART_INDEX].length > TIME_WINDOW)
            dataPoints[CHART_INDEX].shift();
        charts.chart1.render();
  }; 
