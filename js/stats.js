/**
 * 
 */function statsBindings() {
	$(document).on('click', 'input.button-positive', function(event) {
		increasePlayerScore(this);
	});
	$(document).on('click', 'input.button-negative', function(event) {
		decreasePlayerScore(this);
	});
	$(document).on('click', 'input.button-box-score', function(event) {
		getBoxScoreDatat(this);
	});
	$(document).on('click', 'input.button-highlights', function(event) {
		getHighlights(this);
	});
}
 
 /**
  * Updates the total points for that particular player
  * @param playerId
  */
 function updatePlayerTotal(playerId) {
	 var ones = $('span#current-1-' + playerId).text();
	 var twos = $('span#current-2-' + playerId).text() * 2;
	 var threes = $('span#current-3-' + playerId).text() * 3;
	 var total = (isNaN(parseInt(ones)) ? 0 : parseInt(ones)) + parseInt(twos) + parseInt(threes);
	 console.log(playerId + "ones " + ones);
	 console.log(playerId + "twos " + twos);
	 console.log(playerId + "threes " + threes);
	 console.log(playerId + "total " + total);
	 $('span#player-total-' + playerId).html(" (Total: " + total + ")");
 }
 
 function updateTeamTotal() {
	 var firstQuarterScore = $('input#q1').val();
	 var secondQuarterScore = $('input#q2').val();
	 var thirdQuarterScore = $('input#q3').val();
	 var fourthQuarterScore = $('input#q4').val();
	 var overtimeScore = $('input#ot').val();
	 var total = parseInt(firstQuarterScore) + parseInt(secondQuarterScore) + parseInt(thirdQuarterScore) + parseInt(fourthQuarterScore) + parseInt(overtimeScore);
	 console.log("total: " + total);
	 $('input#home-total').val(total);
 }

 /**
  * 
  * @param that
  */
function getHighlights(that) {
	var highlights = $("textarea#highlights-text").val();
	var teamId = $(that).data("team-id");
	var teamTable = $("table#team-container");
	var scheduleId = $(teamTable).data("schedule-id");
	console.log("highlights: " + highlights);
	console.log("tid: " + teamId);
	console.log("sid: " + scheduleId);
	updateHighlights(highlights, teamId, scheduleId);
}

/**
 * 
 * @param that
 */
function increasePlayerScore(that) {
	var playerId = $(that).data("player-id");
	var score = $(that).data("score");
	var change = "increase";
	updatePlayerScore(playerId, score, change);
	// If player makes a FTM then increment the FTA as well
	if(score == '1')
		updatePlayerScore(playerId, '1a', change);
}

/**
 * 
 */
function decreasePlayerScore(that) {
	var playerId = $(that).data("player-id");
	var score = $(that).data("score");
	var change = "decrease";
	updatePlayerScore(playerId, score, change);
	if(score == '1')
		updatePlayerScore(playerId, '1a', change);
}

/**
 * 
 * @param that
 */
function getBoxScoreDatat(that) {
	var quarter = $(that).data("quarter");
	var teamId = $(that).data("team-id");
	var teamTable = $("table#team-container");
	var scheduleId = $(teamTable).data("schedule-id");
	var score = $("input#" + quarter + '-' + teamId).val();
	console.log("quarter: " + quarter);
	console.log("team id: " + teamId);
	console.log("schedule id: " + scheduleId);
	console.log("score: " + score);
	updateBoxScore(quarter, score, teamId, scheduleId);
}

function updateHighlights(highlights, teamId, scheduleId) {
	var url = './stats';
	var subcmd = "update-highlights"
	$.ajax({
		type : "POST",
		url : url,
		data : {
			subcmd : subcmd,
			highlights : highlights,
			teamId : teamId,
			scheduleId : scheduleId
		},
		success : function(data) {
			console.log("update highlight success");
		}
	});
}

function updateBoxScore(quarter, score, teamId, scheduleId) {
	var url = './stats?subcmd=update-box-score&quarter=' + quarter + '&score='
			+ score + '&teamId=' + teamId + '&scheduleId=' + scheduleId;
	console.log("URL: " + url);
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		success : function(data) {
			updateTeamTotal();
		}
	});
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
			if (change == 'increase') {
				var currentScore = $('#current-' + score + '-' + playerId)
						.text();
				currentScore++;
				$('#current-' + score + '-' + playerId).html(currentScore);
			} else if (change == 'decrease') {
				var currentScore = $('#current-' + score + '-' + playerId)
						.text();
				currentScore--;
				$('#current-' + score + '-' + playerId).html(currentScore);
			}
			updatePlayerTotal(playerId);
		}
	});
}
