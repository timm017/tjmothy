<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="header">
    <a href="./home">
      <img src="images/logo2.png" />
    </a>
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

    <!-- About" => new MenuItem("ABOUT", "htdocs/view/about.php", "htdocs/images/menu_about.png"), -->
    <!-- Portfolio" => new MenuItem("PORTFOLIO", "htdocs/view/portfolio.php", 
      "htdocs/images/menu_portfolio.png"), -->
    <!-- Services" => new MenuItem("SERVICES", "htdocs/view/services.php", 
      "htdocs/images/menu_services.png"), -->
    <!-- Contact" => new MenuItem("CONTACT", "htdocs/view/contact.php", "htdocs/images/menu_contact.png")); -->
  </xsl:template>
</xsl:stylesheet>