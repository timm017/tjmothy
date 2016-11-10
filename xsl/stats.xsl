<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="header.xsl" />
  <xsl:import href="footer.xsl" />

  <xsl:variable name="subcmd" select="/outertag/subcmd" />

  <xsl:template match="/outertag">
    <html>
      <head>
        <link rel="stylesheet" href="css/maincontainer.css" type="text/css" />
        <link rel="stylesheet" href="css/footer.css" type="text/css" />
        <link rel="stylesheet" href="css/header.css" type="text/css" />
        <link rel="stylesheet" href="css/menu.css" type="text/css" />
        <link rel="stylesheet" href="css/stats.css" type="text/css" />
        <script language="JavaScript" src="js/jquery-1.10.2.min.js"
          type="text/javascript" />
        <script language="JavaScript" src="js/stats.js" type="text/javascript" />
      </head>
      <body>
        <div class="wrapper">
          <div id="services">
            <p>
              <strong>
                <xsl:value-of select="team/team_name" />
                Basketball stats.
              </strong>
            </p>
            <xsl:choose>
              <xsl:when test="$subcmd = 'login'">
                <xsl:call-template name="login" />
              </xsl:when>
              <xsl:when test="$subcmd = 'stats-view'">
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
    <form action="./stats" method="POST">
      <label>
        <b>Username</b>
      </label>
      <br />
      <input type="text" placeholder="Enter Phone Number" name="phonenumber" />
      <p />
      <label>
        <b>Password</b>
      </label>
      <br />
      <input type="password" placeholder="Enter Password" name="password" />
      <p />
      <button type="submit">Login</button>
      <input type="hidden" name="subcmd" value="login" />
    </form>
    <xsl:if test="login/@success = 'false'">
      <p style="color:red;">
        <xsl:text>Incorrect username or password for.</xsl:text>
      </p>
    </xsl:if>
  </xsl:template>

  <xsl:template name="stats-view">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="team">
    <ul style="list-style-type: none;">
      <xsl:apply-templates />
    </ul>
  </xsl:template>

  <!-- pos-id-value -->
  <!-- neg-id-value -->
  <xsl:template match="player">
    <div id="player-container">
      <li>
        <xsl:apply-templates />
      </li>
      <div id="current-points">
        <xsl:text>1s: </xsl:text>
        <span id="current-1-{id}">
          <xsl:value-of select="current_scores/one_points" />
        </span>
        <xsl:text>2s: </xsl:text>
        <span id="current-2-{id}">
          <xsl:value-of select="current_scores/two_points" />
        </span>
        <xsl:text>3s: </xsl:text>
        <span id="current-3-{id}">
          <xsl:value-of select="current_scores/three_points" />
        </span>
      </div>
      <div id="point-buttons">
        <div id="point-buttons-positive">
          <input data-player-id="{id}" data-score="1" type="button"
            value="+1" />
          <input data-player-id="{id}" data-score="2" type="button"
            value="+2" />
          <input data-player-id="{id}" data-score="3" type="button"
            value="+3" />
        </div>
        <div id="point-buttons-negative">
          <input data-player-id="{id}" data-score="1" type="button"
            value="-1" />
          <input data-player-id="{id}" data-score="2" type="button"
            value="-2" />
          <input data-player-id="{id}" data-score="3" type="button"
            value="-3" />
        </div>
      </div>
    </div>
  </xsl:template>

  <xsl:template match="first_name">
    <xsl:apply-templates />
    <xsl:text> </xsl:text>
  </xsl:template>

  <xsl:template match="last_name">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="id|subcmd|school_name|team_name|team_id|current_scores|league_id" />

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>