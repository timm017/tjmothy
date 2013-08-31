<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="header.xsl" />
  <xsl:import href="footer.xsl" />

  <xsl:template match="/outertag">
    <html>
      <head>
        <link rel="stylesheet" href="css/maincontainer.css" type="text/css" />
        <link rel="stylesheet" href="css/footer.css" type="text/css" />
        <link rel="stylesheet" href="css/header.css" type="text/css" />
        <link rel="stylesheet" href="css/menu.css" type="text/css" />
        <script type="text/javascript" src="js/jquery-1.10.3.min.js"></script>
      </head>
      <body>
        <div id="header">
          <div class="wrapper">
            <xsl:call-template name="header" />
          </div>
        </div>
        <div class="main-stretch">
          <div id="main-inner-stretch">
            <div class="main-inner-stretch-left">
              <h3 class="main-stretch-header">WEB DESIGN</h3>
              <p>1. This is some sample text for that should be under the header text.</p>
            </div>
            <div class="main-inner-stretch-right">
              <img src="images/home-service-cart.png" />
            </div>
          </div>
        </div>
        <div class="wrapper">
          <div id="contact">
            <xsl:apply-templates />
          </div>
        </div>
        <xsl:call-template name="footer" />
      </body>
    </html>

  </xsl:template>

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>