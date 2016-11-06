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
      </head>
      <body>
        <div class="wrapper">
          <div id="services">
            <p>
              <strong>Basketball stats.</strong>
            </p>
            <xsl:message>
              subcmd:
              <xsl:value-of select="$subcmd" />
            </xsl:message>
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
      <!-- <div class="container" style="background-color:#f1f1f1"> <button 
        type="button" class="cancelbtn">Cancel</button> <span class="psw"> Forgot 
        <a href="#">password?</a> </span> </div> -->
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
    <strong>
      <xsl:value-of select="@id" />
      <xsl:text> </xsl:text>
    </strong>
    <ul>
      <xsl:apply-templates />
    </ul>
  </xsl:template>

  <xsl:template match="player">
    <li>
      <xsl:apply-templates />
    </li>
    <input type="text" name="score" />
    <input type="button" value="+1" />
    <input type="button" value="+2" />
    <input type="button" value="+3" />
  </xsl:template>

  <xsl:template match="first_name">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="last_name">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>