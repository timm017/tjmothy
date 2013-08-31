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
            <iframe width="800" height="275" frameborder="10" scrolling="no" marginheight="0" marginwidth="0"
              src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=media,+pa&amp;aq=&amp;sll=41.117935,-77.604698&amp;sspn=4.658945,9.876709&amp;t=m&amp;ie=UTF8&amp;hq=&amp;hnear=Media,+Delaware,+Pennsylvania&amp;z=14&amp;ll=39.916778,-75.387693&amp;output=embed"></iframe>
            <form action="./contact?subcmd=email" method="POST">
              <table border="0">
                <tr>
                  <td>Name:</td>
                  <td>
                    <input type="field" name="name" />
                  </td>
                </tr>
                <tr>
                  <td>Company:</td>
                  <td>
                    <input type="field" name="company" />
                  </td>
                </tr>
                <tr>
                  <td>Phone:</td>
                  <td>
                    <input type="field" name="phone" />
                  </td>
                </tr>
                <tr>
                  <td>Comment &amp; Questions:</td>
                  <td>
                    <textarea name="comments" rows="5" cols="40"></textarea>
                  </td>
                </tr>
                <tr>
                  <td>
                    <xsl:text> </xsl:text>
                  </td>
                  <td>
                    <input style="width: 100px;" type="submit" name="submit" value="Send" />
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