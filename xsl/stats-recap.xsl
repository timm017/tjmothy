<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:variable name="subcmd" select="/outertag/subcmd" />

  <xsl:variable name="noGame" select="//game/no_game_today" />

  <xsl:template match="/outertag">
    <html>
      <head>
        <link rel="stylesheet" href="css/stats.css" type="text/css" />
        <script language="JavaScript" src="js/jquery-1.10.2.min.js" type="text/javascript" />
        <script language="JavaScript" src="js/stats.js" type="text/javascript" />
      </head>
      <body>
        <div class="wrapper-stats">
          <div id="stats">
            <p>
              <h2>
                <strong>
                  <xsl:value-of select="team/school_name" />
                  Basketball Recap.
                </strong>
                <p>
                 Total Score:
                <xsl:value-of select="//current_team_scores/first_quarter + //current_team_scores/second_quarter + //current_team_scores/third_quarter + //current_team_scores/fourth_quarter + //current_team_scores/overtime" />
                </p>
              </h2>
            </p>
            <xsl:apply-templates />
          </div>
        </div>
        <script type="text/javascript">
          <xsl:text>statsBindings();</xsl:text>
        </script>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="team_players">
    <table>
      <th>Players</th>
      <th>Score</th>
      <xsl:apply-templates />
    </table>
  </xsl:template>

  <xsl:template match="player">
    <p>
      <xsl:value-of select="current_scores/one_points + (current_scores/two_points * 2) + (current_scores/three_points * 3)" />
      <xsl:text> --- </xsl:text>
      <xsl:apply-templates />
    </p>
  </xsl:template>

  <xsl:template match="first_name">
    <xsl:apply-templates />
    <xsl:text> </xsl:text>
  </xsl:template>

  <xsl:template match="last_name">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="current_scores">
    <tr>
      <td>
        asdf
        <xsl:apply-templates />
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="current_team_scores|game|id|subcmd|school_name|team_name|team_id|current_scores|league_id" />

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>
