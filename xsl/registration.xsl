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
            <form action="./registration?subcmd=register" method="POST">
              <table border="0">
                <tr>
                  <td>First Name:</td>
                  <td>
                    <input type="field" name="name" />
                  </td>
                </tr>
                <tr>
                  <td>Last Name:</td>
                  <td>
                    <input type="field" name="company" />
                  </td>
                </tr>
                <tr>
                  <td>Username:</td>
                  <td>
                    <input type="field" name="phone" />
                  </td>
                </tr>
                <tr>
                  <td>Email:</td>
                  <td>
                    <input type="field" name="phone" />
                  </td>
                </tr>
                <tr>
                  <td>Password:</td>
                  <td>
                    <input type="field" name="phone" />
                  </td>
                </tr>
                <tr>
                  <td>Repeat password:</td>
                  <td>
                    <input type="field" name="phone" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <xsl:text> </xsl:text>
                  </td>
                  <td>
                    <input style="width: 100px;" type="submit"
                      name="submit" value="Send" />
                  </td>
                </tr>
              </table>
            </form>
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