<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="footer">
    <div id="footer-outer">
      <div id="footer">
        <ul>
          <li class="title">INFO</li>
          <li>
            <a href="./contact">CONTACT</a>
          </li>
          <li>
            <a href="./services">SERVICES</a>
          </li>
          <li>
            <a href="./portfolio">PORTFOLIO</a>
          </li>
          <li>
            <a href="./about">ABOUT</a>
          </li>
        </ul>
        <ul>
          <li class="title">SERVICES</li>
          <li>WEB DEVELOPMENT</li>
          <li>MOBILE DEVELOPMENT</li>
          <li>INTERNET MARKETING</li>
          <li>OPENSOURCE DEVELOPMENT</li>
        </ul>
        <ul>
          <li class="title">SOCIAL MEDIA</li>
          <li>
            <img src="images/footer_facebook.png" />
            <a href="http://www.facebook.com/tim.mckeown.35">FACEBOOK</a>
          </li>
          <li>
            <img src="images/footer_linkedin.png" />
            <a href="http://www.linkedin.com/profile/view?id=154637093">LINKEDIN</a>
          </li>
          <li>
            <img src="images/footer_googleplus.png" />
            <a href="https://plus.google.com/u/0/117569793584660579939/posts/p/pub">GOOGLE+</a>
          </li>
          <li>
            <img src="images/footer_twitter.png" />
            <a href="https://twitter.com/tjmothy">TWITTER</a>
          </li>
          <li>
            <img src="images/footer_rss.png" />
            <xsl:text>RSS</xsl:text>
          </li>
        </ul>
      </div>
    </div>
  </xsl:template>
</xsl:stylesheet>