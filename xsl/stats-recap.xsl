<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:variable name="subcmd" select="/outertag/subcmd" />

  <xsl:variable name="noGame" select="//game/no_game_today" />

  <xsl:variable name="sportId" select="/outertag/game/sport" />

  <xsl:template match="/outertag">
    <html>
      <head>
        <link rel="stylesheet" href="css/stats.css" type="text/css" />
        <script language="JavaScript" src="js/jquery-1.10.2.min.js" type="text/javascript" />
        <script language="JavaScript" src="js/stats.js" type="text/javascript" />
      </head>
      <body>
        <xsl:choose>
          <xsl:when test="$sportId = 13">
            <xsl:call-template name="totalsOnlyBody" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="regularBody" />
          </xsl:otherwise>
        </xsl:choose>
        <script type="text/javascript">
          <xsl:text>statsBindings();</xsl:text>
        </script>
      </body>
    </html>
  </xsl:template>

  <xsl:template name="regularBody">
    <div class="wrapper-stats">
      <a href="./stats?subcmd=logout">Logout</a>
      <div id="stats">
        <p>
          <h2>
            <strong>
              <xsl:value-of select="team/school_name" />
              Recap.
            </strong>
            <p>
              Total Score:
              <xsl:call-template name="totalTable" />
            </p>
            <p>
              <xsl:call-template name="myPlayerTable" />
            </p>
            <p>
              <xsl:call-template name="enemyPlayerTable" />
            </p>
          </h2>
        </p>
        <h2>
          <xsl:value-of select="my_team/team/school_name" />
          Highlights:
        </h2>
        <p>
          <xsl:value-of select="my_team/current_team_scores/highlights" />
        </p>
      </div>
    </div>
  </xsl:template>

  <xsl:template name="totalsOnlyBody">
    <div class="wrapper-stats">
      <a href="./stats?subcmd=logout">Logout</a>
      <div id="stats">
        <p>
          <h2>
            <strong>
              <xsl:value-of select="team/school_name" />
              Recap.
            </strong>
            <p>
              <xsl:value-of select="my_team/team/school_name" />
              Total Score:
              <xsl:value-of select="my_team/total" />
            </p>
            <p>
              <xsl:value-of select="enemy_team/team/school_name" />
              Total Score:
              <xsl:value-of select="enemy_team/total" />
            </p>
          </h2>
        </p>
      </div>
    </div>
  </xsl:template>

  <xsl:template name="totalTable">
    <table border="1">
      <th>Team</th>
      <th>1st</th>
      <th>2nd</th>
      <th>3rd</th>
      <th>4th</th>
      <th>OT</th>
      <th>Total</th>
      <xsl:apply-templates select="my_team" />
      <xsl:apply-templates select="enemy_team" />
    </table>
  </xsl:template>

  <xsl:template match="my_team">
    <tr>
      <td>
        <xsl:value-of select="team/school_name" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/first_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/second_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/third_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/fourth_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/overtime" />
      </td>
      <td>
        <xsl:value-of
          select="current_team_scores/first_quarter + current_team_scores/second_quarter + current_team_scores/third_quarter + current_team_scores/fourth_quarter + current_team_scores/overtime" />
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="enemy_team">
    <tr>
      <td>
        <xsl:value-of select="team/school_name" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/first_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/second_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/third_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/fourth_quarter" />
      </td>
      <td>
        <xsl:value-of select="current_team_scores/overtime" />
      </td>
      <td>
        <xsl:value-of
          select="current_team_scores/first_quarter + current_team_scores/second_quarter + current_team_scores/third_quarter + current_team_scores/fourth_quarter + current_team_scores/overtime" />
      </td>
    </tr>
  </xsl:template>

  <xsl:template name="myPlayerTable">
    <xsl:value-of select="/outertag/my_team/team/school_name" />
    <xsl:text> Players</xsl:text>
    <table border="1">
      <th>Player</th>
      <th>Fouls</th>
      <th>FTM</th>
      <th>FTA</th>
      <th>FG2</th>
      <th>FG3</th>
      <th>Total</th>
      <xsl:apply-templates select="team_players" />
    </table>
  </xsl:template>

  <xsl:template name="enemyPlayerTable">
    <xsl:value-of select="/outertag/enemy_team/team/school_name" />
    <xsl:text> Players</xsl:text>
    <table border="1">
      <th>Player</th>
      <th>Fouls</th>
      <th>FTM</th>
      <th>FTA</th>
      <th>FG2</th>
      <th>FG3</th>
      <th>Total</th>
      <xsl:apply-templates select="enemy_team_players" />
    </table>
  </xsl:template>

  <xsl:template match="team_players">
    <xsl:for-each select="player">
      <tr>
        <xsl:apply-templates select="." />
      </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="enemy_team_players">
    <xsl:for-each select="player">
      <tr>
        <xsl:apply-templates select="." />
      </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="player">
    <td>
      <xsl:value-of select="first_name" />
      <xsl:text> </xsl:text>
      <xsl:value-of select="last_name" />
    </td>
    <td>
      <xsl:value-of select="current_scores/fouls" />
    </td>
    <td>
      <xsl:value-of select="current_scores/one_points" />
    </td>
    <td>
      <xsl:value-of select="current_scores/one_points_attempted" />
    </td>
    <td>
      <xsl:value-of select="current_scores/two_points" />
    </td>
    <td>
      <xsl:value-of select="current_scores/three_points" />
    </td>
    <td>
      <xsl:value-of select="current_scores/one_points + (current_scores/two_points * 2) + (current_scores/three_points * 3)" />
    </td>
  </xsl:template>

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>
