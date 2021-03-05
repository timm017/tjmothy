<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:variable name="subcmd" select="/outertag/subcmd" />

  <xsl:variable name="noGame" select="//game[1]/no_game_today" />

  <xsl:variable name="scheduleId" select="//game[1]/schedule_id" />

  <xsl:variable name="sportId" select="//game[1]/sport" />

  <xsl:template match="/outertag">
    <html>
      <head>
        <link rel="stylesheet" href="css/stats.css" type="text/css" />
        <script language="JavaScript" src="js/jquery-1.10.2.min.js" type="text/javascript" />
        <script language="JavaScript" src="js/stats.js" type="text/javascript" />
      </head>
      <body>
        <div class="wrapper-stats">
          <a href="./stats?subcmd=logout">Logout</a>
          <div id="stats">
            <p>
              <h2>
                <strong>
                  <xsl:value-of select="my_team/team/school_name" />
                  <xsl:text> Team Stats</xsl:text>
                </strong>
              </h2>
            </p>
            <xsl:choose>
              <xsl:when test="$noGame = 'true'">
                <xsl:call-template name="noGameToday" />
              </xsl:when>
              <xsl:when test="$subcmd = 'login'">
                <xsl:call-template name="login" />
              </xsl:when>
              <xsl:when test="$subcmd = 'logout'">
                <xsl:call-template name="login" />
              </xsl:when>
              <!-- For now we are using this for all sports (total scores only) -->
              <xsl:when test="$sportId = '13' or $sportId = '5' or $sportId = $sportId">
                <xsl:call-template name="baseballStats" />
              </xsl:when>
              <xsl:when test="$subcmd = 'stats-view'">
                <xsl:call-template name="stats-view" />
                <xsl:call-template name="finalSubmit" />
              </xsl:when>
              <xsl:when test="$subcmd = 'stats-recap'">
                <xsl:call-template name="stats-view" />
              </xsl:when>
              <xsl:otherwise>
                <xsl:apply-templates />
              </xsl:otherwise>
            </xsl:choose>
          </div>
        </div>
        <script type="text/javascript">
          <xsl:text>statsBindings();</xsl:text>
        </script>
      </body>
    </html>
  </xsl:template>

  <xsl:template name="login">
    <div class="login-block">
      <form action="./stats" method="POST">
        <img src="images/login.png" />
        <p />
        <input type="text" placeholder="Phone number or email" name="phonenumber" />
        <input type="password" placeholder="Enter Password" name="password" />
        <button type="submit" class="button-gray button">Login</button>
        <input type="hidden" name="subcmd" value="login" />
      </form>
      <!-- <xsl:if test="login/@success = 'false'">
        <p style="color:red;">
          <xsl:text>Incorrect username or password for.</xsl:text>
        </p>
      </xsl:if> -->
    </div>
  </xsl:template>

  <xsl:template name="stats-view">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="team_players">
    <xsl:variable name="mySchool" select="/outertag/my_team/team/school_name" />
    <p>
      <span id="my-team-players-toggle">
        <xsl:text> Open/Close </xsl:text>
        <xsl:value-of select="$mySchool" />
        <xsl:text>Players</xsl:text>
      </span>
    </p>
    <div id="my-team-players-container">
      <xsl:apply-templates />
    </div>
  </xsl:template>

  <xsl:template match="enemy_team_players">
    <xsl:variable name="enemySchool" select="/outertag/enemy_team/team/school_name" />
    <p>
      <span id="enemy-team-players-toggle">
        <xsl:text> Open/Close </xsl:text>
        <xsl:value-of select="$enemySchool" />
        <xsl:text> Players</xsl:text>
      </span>
    </p>
    <div id="enemy-team-players-container">
      <xsl:apply-templates />
    </div>
  </xsl:template>

  <xsl:template match="enemy_team">
    <xsl:variable name="teamId" select="team/id" />
    <table id="enemy-team-container" data-schedule-id="{$scheduleId}" data-team-id="{$teamId}">
      <caption>
        <xsl:value-of select="team/school_name" />
        <xsl:text> Box Scores</xsl:text>
      </caption>
      <th>
        <xsl:text>Quarter</xsl:text>
      </th>
      <th>
        <xsl:text>Score</xsl:text>
      </th>
      <th>
        <xsl:text>Update</xsl:text>
      </th>
      <tr>
        <td>
          <xsl:text>1st:</xsl:text>
        </td>
        <td>
          <input id="q1-{$teamId}" type="text" value="{current_team_scores/first_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q1" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>2nd:</xsl:text>
        </td>
        <td>
          <input id="q2-{$teamId}" type="text" value="{current_team_scores/second_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q2" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>3rd:</xsl:text>
        </td>
        <td>
          <input id="q3-{$teamId}" type="text" value="{current_team_scores/third_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q3" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>4th:</xsl:text>
        </td>
        <td>
          <input id="q4-{$teamId}" type="text" value="{current_team_scores/fourth_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q4" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>OT:</xsl:text>
        </td>
        <td>
          <input id="ot-{$teamId}" type="text" value="{current_team_scores/overtime}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="ot" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>TOTAL:</xsl:text>
        </td>
        <td>
          <input readonly="true" id="enemy-total" type="text"
            value="{current_team_scores/first_quarter + current_team_scores/second_quarter + current_team_scores/third_quarter + current_team_scores/fourth_quarter + current_team_scores/overtime}" />
        </td>
        <td />
      </tr>
    </table>
  </xsl:template>

  <xsl:template match="my_team">
    <xsl:variable name="teamId" select="team/id" />
    <table id="home-team-container" data-schedule-id="{$scheduleId}" data-team-id="{$teamId}">
      <caption>
        <xsl:value-of select="team/school_name" />
        <xsl:text> Box Scores</xsl:text>
      </caption>
      <th>
        <xsl:text>Quarter</xsl:text>
      </th>
      <th>
        <xsl:text>Score</xsl:text>
      </th>
      <th>
        <xsl:text>Update</xsl:text>
      </th>
      <tr>
        <td>
          <xsl:text>1st:</xsl:text>
        </td>
        <td>
          <input id="q1-{$teamId}" type="text" value="{current_team_scores/first_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q1" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>2nd:</xsl:text>
        </td>
        <td>
          <input id="q2-{$teamId}" type="text" value="{current_team_scores/second_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q2" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>3rd:</xsl:text>
        </td>
        <td>
          <input id="q3-{$teamId}" type="text" value="{current_team_scores/third_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q3" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>4th:</xsl:text>
        </td>
        <td>
          <input id="q4-{$teamId}" type="text" value="{current_team_scores/fourth_quarter}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="q4" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>OT:</xsl:text>
        </td>
        <td>
          <input id="ot-{$teamId}" type="text" value="{current_team_scores/overtime}" />
        </td>
        <td>
          <input class="button-box-score" data-team-id="{$teamId}" data-quarter="ot" type="button" value="Update" />
        </td>
      </tr>
      <tr>
        <td>
          <xsl:text>TOTAL:</xsl:text>
        </td>
        <td>
          <input readonly="true" id="home-total" type="text"
            value="{current_team_scores/first_quarter + current_team_scores/second_quarter + current_team_scores/third_quarter + current_team_scores/fourth_quarter + current_team_scores/overtime}" />
        </td>
        <td />
      </tr>
    </table>
  </xsl:template>

  <xsl:template name="finalSubmit">
    <br />
    <strong>
      <xsl:value-of select="//my_team/team/school_name" />
      <xsl:text> Highlights</xsl:text>
    </strong>
    <p>
      <textarea id="highlights-text" rows="4" cols="50">
        <xsl:value-of select="//my_team/current_team_scores/highlights" />
      </textarea>
      <input class="button-highlights" data-team-id="{//my_team/team/id}" type="button" value="Update" />
    </p>
    <p>
      <form action="./stats" method="POST" id="stats-form">
        <input class="button-final-submit" type="submit" value="Final Submit!" />
        <input type="hidden" name="subcmd" value="final-submit" />
        <input type="hidden" name="teamId" value="{/outertag/my_team/team/id}" />
        <input type="hidden" name="enemyTeamId" value="{/outertag/enemy_team/team/id}" />
        <input type="hidden" name="scheduleId" value="{$scheduleId}" />
      </form>
    </p>
  </xsl:template>

  <!-- pos-id-value -->
  <!-- neg-id-value -->
  <!-- Order: FOULS FTM FTA 2FG 3FG -->
  <xsl:template match="player">
    <xsl:variable name="playerTotal">
      <xsl:value-of select="current_scores/one_points + (current_scores/two_points * 2) + (current_scores/three_points * 3)" />
    </xsl:variable>
    <div id="player-container">
      <table>
        <caption>
          <xsl:apply-templates />
          <span class="player-total" id="player-total-{id}">
            <xsl:text> (Total: </xsl:text>
            <xsl:value-of select="$playerTotal" />
            <xsl:text>)</xsl:text>
          </span>
        </caption>
        <th>Fouls</th>
        <th>Ones (FTM)</th>
        <th>Ones (FTA)</th>
        <th>Twos</th>
        <th>Threes</th>
        <th>Rebounds</th>
        <tr>
          <div id="current-points">
            <td>
              <span id="current-f-{id}">
                <xsl:value-of select="current_scores/fouls" />
              </span>
            </td>
            <td>
              <span id="current-1-{id}">
                <xsl:value-of select="current_scores/one_points" />
              </span>
            </td>
            <td>
              <span id="current-1a-{id}">
                <xsl:value-of select="current_scores/one_points_attempted" />
              </span>
            </td>
            <td>
              <span id="current-2-{id}">
                <xsl:value-of select="current_scores/two_points" />
              </span>
            </td>
            <td>
              <span id="current-3-{id}">
                <xsl:value-of select="current_scores/three_points" />
              </span>
            </td>
            <td>
              <span id="current-r-{id}">
                <xsl:value-of select="current_scores/rebounds" />
              </span>
            </td>
          </div>
        </tr>
        <tr>
          <td>
            <input class="button-positive" data-player-id="{id}" data-score="f" type="button" value="+1" />
          </td>
          <td>
            <input class="button-positive" data-player-id="{id}" data-score="1" type="button" value="+1" />
          </td>
          <td>
            <input class="button-positive" data-player-id="{id}" data-score="1a" type="button" value="+1" />
          </td>
          <td>
            <input class="button-positive" data-player-id="{id}" data-score="2" type="button" value="+2" />
          </td>
          <td>
            <input class="button-positive" data-player-id="{id}" data-score="3" type="button" value="+3" />
          </td>
          <td>
            <input class="button-positive" data-player-id="{id}" data-score="r" type="button" value="+1" />
          </td>
          <tr>
            <td>
              <input class="button-negative" data-player-id="{id}" data-score="f" type="button" value="-1" />
            </td>
            <td>
              <input class="button-negative" data-player-id="{id}" data-score="1" type="button" value="-1" />
            </td>
            <td>
              <input class="button-negative" data-player-id="{id}" data-score="1a" type="button" value="-1" />
            </td>
            <td>
              <input class="button-negative" data-player-id="{id}" data-score="2" type="button" value="-2" />
            </td>
            <td>
              <input class="button-negative" data-player-id="{id}" data-score="3" type="button" value="-3" />
            </td>
            <td>
              <input class="button-negative" data-player-id="{id}" data-score="r" type="button" value="-1" />
            </td>
          </tr>
        </tr>
      </table>
    </div>
  </xsl:template>

  <xsl:template match="first_name">
    <xsl:apply-templates />
    <xsl:text> </xsl:text>
  </xsl:template>

  <xsl:template match="last_name">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template name="baseballStats">
    <xsl:variable name="myTeamId" select="my_team/team/id" />
    <xsl:variable name="enemyTeamId" select="enemy_team/team/id" />
    <div class="baseball-my-team-container">
      <!-- total MY team score -->
      <table id="home-team-total-container" data-schedule-id="{$scheduleId}" data-team-id="{$myTeamId}">
        <caption>
          <xsl:value-of select="my_team/team/school_name" />
          <xsl:text> Total Score</xsl:text>
        </caption>
        <tr>
          <td>
            <input class="team-score" id="total-{$myTeamId}" data-schedule-id="{$scheduleId}" data-team-id="{$myTeamId}" type="number" value="" />
          </td>
          <td>
            <!-- <input class="button-total-score" data-team-id="{$myTeamId}" type="button" value="Update" /> -->
          </td>
        </tr>
      </table>
      <!-- <xsl:if test="count(team_players/player) &gt; 0">
        <xsl:call-template name="baseballMyPlayers" />
      </xsl:if> -->
    </div>

    <div class="baseball-enemy-team-container">
      <!-- total enemy score -->
      <table id="enemy-team-total-container" data-schedule-id="{$scheduleId}" data-team-id="{$enemyTeamId}" class="baseball-enemy-team-container">
        <caption>
          <xsl:value-of select="enemy_team/team/school_name" />
          <xsl:text> Total Score</xsl:text>
        </caption>
        <tr>
          <td>
            <input class="team-score" id="total-{$enemyTeamId}" data-schedule-id="{$scheduleId}" data-team-id="{$enemyTeamId}" type="number" value="" />
          </td>
          <td>
            <!-- <input class="button-total-score" data-team-id="{$enemyTeamId}" type="button" value="Update" /> -->
          </td>
        </tr>
      </table>
      <!-- <xsl:if test="count(team_players/player) &gt; 0">
        <xsl:call-template name="baseballEnemyPlayers" />
      </xsl:if> -->
    </div>
    <!-- BASEBALL -->
    <form action="./stats" method="POST" id="stats-form">
      <input class="button-final-submit" id="final-submit-baseball" type="submit" value="Final Submit!" />
      <input type="hidden" name="subcmd" value="final-submit" />
      <input type="hidden" name="teamId" value="{$myTeamId}" />
      <input type="hidden" name="enemyTeamId" value="{$enemyTeamId}" />
      <input type="hidden" name="scheduleId" value="{$scheduleId}" />
    </form>
  </xsl:template>

  <xsl:template name="baseballMyPlayers">
    <table id="pitchers-container">
      <caption>
        <xsl:text> Pitchers</xsl:text>
      </caption>
      <xsl:for-each select="team_players/player ">
        <tr>
          <td>
            <xsl:text>#</xsl:text>
            <xsl:value-of select="number" />
            <xsl:text> </xsl:text>
            <xsl:value-of select="first_name" />
            <xsl:text> </xsl:text>
            <xsl:value-of select="last_name" />
          </td>
          <td>
            <input class="player-pitches" id="pitches-{id}" data-player-id="{id}" data-schedule-id="{$scheduleId}" type="number" value="" />
          </td>
          <td>
            <!-- <input class="button-total-pitches" data-player-id="{id}" data-schedule-id="{$scheduleId}" type="button" value="Update" /> -->
          </td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>

  <xsl:template name="baseballEnemyPlayers">
    <table id="pitchers-container">
      <caption>
        <xsl:text> Pitchers</xsl:text>
      </caption>
      <xsl:for-each select="enemy_team_players/player ">
        <tr>
          <td>
            <xsl:text>#</xsl:text>
            <xsl:value-of select="number" />
            <xsl:text> </xsl:text>
            <xsl:value-of select="first_name" />
            <xsl:text> </xsl:text>
            <xsl:value-of select="last_name" />
          </td>
          <td>
            <input class="player-pitches" id="pitches-{id}" data-player-id="{id}" data-schedule-id="{$scheduleId}" type="number" value="" />
          </td>
          <td>
            <!-- <input class="button-total-pitches" data-player-id="{id}" data-schedule-id="{$scheduleId}" type="button" value="Update" /> -->
          </td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>

  <xsl:template name="noGameToday">
    <xsl:variable name="tabulatorEmail" select="//tabulator/email" />
    <xsl:text>No game today. If you added or missed a game</xsl:text>
    <xsl:text> - </xsl:text><a href="mailto:{$tabulatorEmail}">Email tabulator</a>
  </xsl:template>

  <xsl:template match="user|is_home_team|current_team_scores|game|id|subcmd|school_name|team_name|team_id|current_scores|league_id" />

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>
