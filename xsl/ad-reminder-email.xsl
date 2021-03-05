<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:variable name="subcmd" select="/outertag/subcmd" />

  <xsl:template match="/outertag">
    <html>
      <head />
      <body>
          <div>
            <xsl:call-template name="body-message" />
          </div>
      </body>
    </html>
  </xsl:template>

  <xsl:template name="body-message">
    <p>
      <xsl:text>Your school had a game last night that was not reported to districtscores.com. Please log in and enter the score to keep the rankings up to date.<br/><br/>Thank you,<br.>DistrictScores.com";</xsl:text>
    </p>
  </xsl:template>

  <xsl:template match="message">
    <p>
      <xsl:apply-templates />
    </p>
  </xsl:template>
</xsl:stylesheet>
