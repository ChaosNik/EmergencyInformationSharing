function fillData(myData) {

	var objectData = JSON.parse(myData);
	var ctx = document.getElementById("myChart").getContext('2d');
	var myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"],
			datasets: [{
				label: 'Broj korisnika',
				data: objectData,
				backgroundColor: 'blue',
				borderColor: 'black',
				borderWidth: 1
			}]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					},
					scaleLabel: {
						display: true,
						labelString: 'Korisnici'
					}
				}],
				xAxes: [{
					display: true,
					scaleLabel: {
						display: true,
						labelString: 'Sati unazad'
					}
				}]
			},
			responsive: true,
			maintainAspectRatio: false,
		}
	});
}

