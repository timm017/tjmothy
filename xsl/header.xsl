<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="header">
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new
      Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-34846059-1', 'auto');
      ga('send', 'pageview');

    </script>
    <!-- <a href="./home"> -->
    <!-- <img src="images/logo.png" /> -->
    <!-- </a> -->
    <div id="signin" style="margin-top:10px">
      <form>
        <input type="text"></input>
        <input type="text"></input>
        <input type="submit" value="Signin"></input>
        <br />
        <a href="./registration">Register</a>
      </form>
    </div>
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
        <a href="./websitechecker">
          <img src="images/13-target.png" />
          <br />
          <xsl:text>Checker</xsl:text>
        </a>
      </li>
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
