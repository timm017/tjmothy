<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="header.xsl" />
  <xsl:import href="footer.xsl" />

  <xsl:variable name="pi" select="document('portfolio-items.xml')" />

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
          <xsl:call-template name="android-items" />
          <xsl:call-template name="web-items" />
        </div>
        <xsl:call-template name="footer" />
      </body>
    </html>
  </xsl:template>

  <xsl:template name="web-items">
    <h1>Web</h1>
    <xsl:apply-templates select="$pi/portfolio-items/web-items" />
  </xsl:template>

  <xsl:template name="android-items">
    <h1>Android</h1>
    <xsl:apply-templates select="$pi/portfolio-items/android-items" />
  </xsl:template>

  <xsl:template match="item">
    <div id="portfolio">
      <p>
        <table border='0'>
          <xsl:apply-templates />
        </table>
      </p>
    </div>
  </xsl:template>

  <xsl:template match="image">
    <tr>
      <td class='image' rowspan='3'>
        <img src='{.}' />
      </td>
      <td class='title'>
        <xsl:value-of select="preceding-sibling::title" />
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="title" />

  <xsl:template match="description">
    <tr>
      <td class='description'>
        <xsl:apply-templates />
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="link">
    <tr>
      <td class='link'>
        <xsl:text>Visit: </xsl:text>
        <a href='{.}'>
          <xsl:value-of select="preceding-sibling::title" />
        </a>
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>