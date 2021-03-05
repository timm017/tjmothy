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
          <div id="contact">
            <br />
            <br />
            <form action="./websitechecker?subcmd=check" method="POST">
              <table border="0">
                <tr>
                  <td>Website:</td>
                  <td>
                    <input type="field" name="website" />
                  </td>
                </tr>
                <tr>
                  <td>Search:</td>
                  <td>
                    <input type="field" name="search" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <xsl:text> </xsl:text>
                  </td>
                  <td>
                    <input style="width: 100px;" type="submit"
                      name="submit" value="Check" />
                  </td>
                </tr>
              </table>
            </form>
          </div>
          <xsl:apply-templates />
        </div>
        <xsl:call-template name="footer" />
      </body>
    </html>
  </xsl:template>

  <xsl:template match="websitechecker_tool">
    <xsl:choose>
      <xsl:when test="result = 'found'">
        <font face="verdana" color="green">Found!</font>
      </xsl:when>
      <xsl:when test="result='not_found'">
        <font face="verdana" color="red">Not found!</font>
      </xsl:when>
      <xsl:when test="result='malformed_url'">
        <font face="verdana" color="yellow">Invalid URL!</font>
      </xsl:when>
      <xsl:otherwise>
        <font face="verdana" color="yellow">Something went wrong...</font>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>