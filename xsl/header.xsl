<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="header">
    <!-- <a href="./home"> -->
    <!-- <img src="images/logo.png" /> -->
    <!-- </a> -->
    <div id="header-title">
      <a href="./home">
        <span class="title-first">
          <xsl:text>tjmothy</xsl:text>
        </span>
        <span class="title-second">
          <xsl:text>.com</xsl:text>
        </span>
      </a>
    </div>
    <ul id="menu">
      <li class="button">
        <a href="./about">
          <img src="images/menu_about.png" />
          <br />
          <xsl:text>About</xsl:text>
        </a>
      </li>
      <li class="button">
        <a href="./portfolio">
          <img src="images/menu_portfolio.png" />
          <br />
          <xsl:text>Portfolio</xsl:text>
        </a>
      </li>
      <li class="button">
        <a href="./services">
          <img src="images/menu_services.png" />
          <br />
          <xsl:text>Services</xsl:text>
        </a>
      </li>
      <li class="button">
        <a href="./contact">
          <img src="images/menu_contact.png" />
          <br />
          <xsl:text>Contact</xsl:text>
        </a>
      </li>
    </ul>
  </xsl:template>
</xsl:stylesheet>
