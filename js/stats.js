function statsBindings() {
	$(document).on('click', 'input.button-positive', function(event) {
		increasePlayerScore(this);
	});
	$(document).on('click', 'input.button-negative', function(event) {
		decreasePlayerScore(this);
	});
	$(document).on('click', '#home-team-container input.button-box-score', function(event) {
		getBoxScoreData(this, 'home');
	});
	$(document).on('click', '#enemy-team-container input.button-box-score', function(event) {
		getBoxScoreData(this, 'enemy');
	});
	$(document).on('click', 'input.button-highlights', function(event) {
		getHighlights(this);
	});
	$(document).on('click', 'span#my-team-players-toggle', function(event) {
		$('div#my-team-players-container').toggle();
	});
	$(document).on('click', 'span#enemy-team-players-toggle', function(event) {
		$('div#enemy-team-players-container').toggle();
	});
	$(document).on('click', 'div#player-container table caption', function(event) {
		$(this).siblings('tbody').toggle();
	});
	//baseball stuff
	$(document).on('click', '#enemy-team-total-container input.button-total-score', function(event) {
		getTotalScoreData(this, 'enemy');
	});
	$(document).on('click', '#home-team-total-container input.button-total-score', function(event) {
		getTotalScoreData(this, 'home');
	});
	$(document).on('click', 'input.button-total-pitches', function(event) {
		getTotalPitchData(this);
	});
	// When the user clicks out of the pitch input field it will update the DB.
	// Sometimes they forget to hit update for each one
	$(document).on('blur', 'input.player-pitches', function(event) {
		var playerId = $(this).data("player-id");
		var pitches = $(this).val();
		var scheduleId = $(this).data("schedule-id");
		if(pitches != '')  {
			submitPitchTotal(playerId, pitches, scheduleId);
		}
	});
	$(document).on('blur', 'input.team-score', function(event) {
		var teamId = $(this).data("team-id");
		var scheduleId = $(this).data("schedule-id");
		var score = $(this).val();
		if(score != '') {
			console.log(teamId + " : " + scheduleId + " : " + score);
			submitTeamTotal(score, teamId, scheduleId);	
		}
	});
	
	$('div#enemy-team-players-container').hide();
	collapseAllPlayers();
}
 
 function collapseAllPlayers()
 {
	 $("div#player-container table tbody").each(function() {
		 $(this).hide();
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
//	 console.log(playerId + "ones " + ones);
//	 console.log(playerId + "twos " + twos);
//	 console.log(playerId + "threes " + threes);
//	 console.log(playerId + "total " + total);
	 $('span#player-total-' + playerId).html(" (Total: " + total + ")");
 }
 
 function updateTeamTotal(team) {
	 var teamTable = $("table#" + team + "-team-container");
	 var teamId = $(teamTable).data("team-id");
	 var firstQuarterScore = parseInt($('input#q1-' + teamId).val());
	 var secondQuarterScore = parseInt($('input#q2-' + teamId).val());
	 var thirdQuarterScore = parseInt($('input#q3-' + teamId).val());
	 var fourthQuarterScore = parseInt($('input#q4-' + teamId).val());
	 var overtimeScore = parseInt($('input#ot-' + teamId).val());
	 var total = (firstQuarterScore + secondQuarterScore + thirdQuarterScore + fourthQuarterScore + overtimeScore);
	 console.log("total: " + total);
	 $('input#' + team + '-total').val(total);
 }

 /**
  * 
  * @param that
  */
function getHighlights(that) {
	var highlights = $("textarea#highlights-text").val();
	var teamId = $(that).data("team-id");
	var teamTable = $("table#home-team-container");
	var scheduleId = $(teamTable).data("schedule-id");
//	console.log("highlights: " + highlights);
//	console.log("tid: " + teamId);
//	console.log("sid: " + scheduleId);
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
function getBoxScoreData(that, team) {
	var quarter = $(that).data("quarter");
	var teamId = $(that).data("team-id");
	var teamTable = $("table#" + team + "-team-container");
	var scheduleId = $(teamTable).data("schedule-id");
	var score = $("input#" + quarter + '-' + teamId).val();
	updateBoxScore(quarter, score, teamId, scheduleId, team);
}

function getTotalScoreData(that, team) {
	var teamId = $(that).data("team-id");
	var teamTable = $("table#" + team + "-team-total-container");
	var scheduleId = $(teamTable).data("schedule-id");
	var total = $("input#total-" + teamId).val();
	submitTeamTotal(total, teamId, scheduleId);
}

function getTotalPitchData(that) {
	var playerId = $(that).data("player-id");
	var scheduleId = $(that).data("schedule-id");
	var pitches = $("input#pitches-" + playerId).val();
	submitPitchTotal(playerId, pitches, scheduleId);
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

function updateBoxScore(quarter, score, teamId, scheduleId, team) {
	var url = './stats?subcmd=update-box-score&quarter=' + quarter + '&score='
			+ score + '&teamId=' + teamId + '&scheduleId=' + scheduleId;
	console.log("URL: " + url);
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		success : function(data) {
			updateTeamTotal(team);
		}
	});
}

function submitTeamTotal(total, teamId, scheduleId) {
	var url = './stats?subcmd=update-total-score&total='
			+ total + '&teamId=' + teamId + '&scheduleId=' + scheduleId;
	console.log("URL: " + url);
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		success : function(data) {
		}
	});
}

function submitPitchTotal(playerId, pitches, scheduleId) {
	var url = './stats?subcmd=update-pitch-total&pitches='
			+ pitches + '&playerId=' + playerId + '&scheduleId=' + scheduleId;
	console.log("URL: " + url);
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		success : function(data) {
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
