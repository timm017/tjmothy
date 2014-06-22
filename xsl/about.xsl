<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
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
          <xsl:call-template name="about-me" />
        </div>
        <xsl:call-template name="footer" />
      </body>
    </html>
  </xsl:template>

  <xsl:template name="about-me">
    <h1>About</h1>
    <div id="about">
      <p>
        <xsl:text>tjmothy.com is a place for me to share my knowledge and some of the projects and personal interests I've been working on. </xsl:text>
        <xsl:text>I'm always learning new languages, technology, techniques, etc. and enjoy the challenges of each project.  </xsl:text>
        <p>
          <xsl:text>I do freelancing in my free time so please send me an </xsl:text>
          <a href="./contact" class="blue-link">email</a>
          <xsl:text> if you have any questions or projects I can help you with.  </xsl:text>
        </p>
        <p>
          <xsl:text>You can check out my profile on </xsl:text>
          <a href="http://www.linkedin.com/profile/view?id=154637093"
            class="blue-link">LinkedIn</a>
          <xsl:text>.</xsl:text>
        </p>
      </p>
    </div>
  </xsl:template>

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>