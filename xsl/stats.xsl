<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="header.xsl" />
  <xsl:import href="footer.xsl" />

  <xsl:template match="/outertag">
    <html>
      <head>
        <link rel="stylesheet" href="css/maincontainer.css" type="text/css" />
        <link rel="stylesheet" href="css/footer.css" type="text/css" />
        <link rel="stylesheet" href="css/header.css" type="text/css" />
        <link rel="stylesheet" href="css/menu.css" type="text/css" />
      </head>
      <body>
        <div id="header">
          <div class="wrapper">
            <xsl:call-template name="header" />
          </div>
        </div>
        <div class="wrapper">
          <div id="services">
            <p>
              <strong>STATS!</strong>
            </p>
            <xsl:apply-templates />
          </div>
        </div>
        <xsl:call-template name="footer" />
      </body>
    </html>
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