function statsBindings() {

	$(document).on('click', '#point-buttons-positive input', function(event) {
		increasePlayerScore(this);
	});
	
	$(document).on('click', '#point-buttons-negative input', function(event) {
		decreasePlayerScore(this);
	});
}

function increasePlayerScore(that) {
	var playerId = $(that).data("player-id");
	var score = $(that).data("score");
	var change = "increase";
	updatePlayerScore(playerId, score, change);
}

function decreasePlayerScore(that) {
	var playerId = $(that).data("player-id");
	var score = $(that).data("score");
	var change = "decrease";
	updatePlayerScore(playerId, score, change);
}

function updatePlayerScore(playerId, score, change) {

	var url = './stats?subcmd=update-player-score&player_id=' + playerId
			+ '&score=' + score + '&change=' + change;

	console.log("URL: " + url);

	$.ajax({
		type : "POST",
		url : url,
		async : false,
		success : function(data) {
			if(change == 'increase') {
				var currentScore = $('#current-' + score + '-' + playerId).text();
				currentScore++;
				$('#current-' + score + '-' + playerId).html(currentScore);
			}
			else if(change == 'decrease') {
				var currentScore = $('#current-' + score + '-' + playerId).text();
				currentScore--;
				$('#current-' + score + '-' + playerId).html(currentScore);
			}
		}
	});
}