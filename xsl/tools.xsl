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
              <p>
                <h3 style="color:#d95d5d">
                  <img src="images/services_development.png" />
                  <span>DEVELOPMENT</span>
                </h3>
                <hr />
                <ul>
                  <li>Java (including Android development)</li>
                  <li>PHP</li>
                  <li>Javascript (jQuery) and AJAX</li>
                  <li>MySQL and Database Design</li>
                  <li>Web Hosting Setup</li>
                  <li>Domain name registration</li>
                  <li>Apache Server and Administration</li>
                </ul>
              </p>
              <p>
                <h3 style="color:#45a5d4">
                  <img src="images/services_design.png" />
                  <span>DESIGN</span>
                </h3>
                <hr />
                <ul>
                  <li>HTML, XHTML</li>
                  <li>CSS</li>
                  <li>Wordpress customization</li>
                  <li>User Interface Design</li>
                  <li>Online Galleries</li>
                </ul>
              </p>
              <p>
                <h3 style="color:#645d5d">
                  <img src="images/services_solutions.png" />
                  <span>SOLUTIONS</span>
                </h3>
                <hr />
                <ul>
                  <li>CMS (Content Management Systems) such as Wordpress</li>
                  <li>Blogs</li>
                  <li>Online Forums (VBulletin, PHPBB, SMF)</li>
                  <li>Web Hosting Services</li>
                </ul>
              </p>
            </p>
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