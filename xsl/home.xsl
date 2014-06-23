<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
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
        <link href='http://fonts.googleapis.com/css?family=Droid+Sans:700'
          rel='stylesheet' />
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
              <p>
                <xsl:text>Connect with the most popular social media sources.  </xsl:text>
                <xsl:text>Have complete control of your site's content with centralized access.  </xsl:text>
                <xsl:text>Make your business profitable by utilizing the latest technologies.  </xsl:text>
              </p>
            </div>
            <div class="main-inner-stretch-right">
              <img width="150px" height="150px" src="images/web-design-main.png" />
            </div>
          </div>
          <div id="main-inner-stretch">
            <div class="main-inner-stretch-left">
              <h3 class="main-stretch-header">ANDROID DEVELOPMENT</h3>
              <p>
                <xsl:text>Native android development.  </xsl:text>
                <xsl:text>Support for hundreds of different devices, including tablets and phones.  </xsl:text>
                <xsl:text>100% customizable to suit you and your business needs.  </xsl:text>
              </p>
            </div>
            <div class="main-inner-stretch-right">
              <img width="150px" height="150px"
                src="images/android_development_icon.png" />
            </div>
          </div>
          <div id="main-inner-stretch">
            <div class="main-inner-stretch-left">
              <h3 class="main-stretch-header">CONSULTATION</h3>
              <p>
                <xsl:text>Need help or advice on a current or new project?  </xsl:text>
                <xsl:text>We can help with with just about anything.  </xsl:text>
                <a href="./contact" class="blue-link">Shoot us an email!  </a>
              </p>
            </div>
            <div class="main-inner-stretch-right">
              <img width="150px" height="150px" src="images/consultation.png" />
            </div>
          </div>
        </div>
        <div class="wrapper">
          <div class="bottom_container">
            <div id="modal">
              <section>
                <img src="images/icon_develope.jpg" />
                <h1>DEVELOP</h1>
                <p>
                  <xsl:text>Expert web design and development for creating websites and web applications. </xsl:text>
                  <xsl:text>Strengthen your brand and drive sales.  </xsl:text>
                </p>
              </section>
            </div>
            <div id="modal">
              <section>
                <img src="images/icon_manage.jpg" />
                <h1>MANAGE</h1>
                <p>
                  <xsl:text>Have real control over your site from publishing
                  &amp; editing of blog content, social media updates, uploading images and web analytics.</xsl:text>
                </p>
              </section>
            </div>
            <div id="modal">
              <section>
                <img src="images/icon_scale.jpg" />
                <h1>SCALE</h1>
                <p>
                  <xsl:text>Let your site hit its full potential.  </xsl:text>
                  <xsl:text>Our development, hosting, analytics, and SEO will allow your company to grow and expand.</xsl:text>
                </p>
              </section>
            </div>
          </div>
          <div id="contact">
            <xsl:apply-templates />
          </div>
          <div class="long-container">
            <div id="long-container-content">
              <ul>
                <li>
                  <img src="images/icon_apache.jpg" />
                </li>
                <li>
                  <img src="images/icon_mysql.jpg" />
                </li>
                <li>
                  <img src="images/icon_php.jpg" />
                </li>
                <li>
                  <img src="images/icon_linux.jpg" />
                </li>
                <li>
                  <img src="images/icon_wordpress.jpg" />
                </li>
                <li>
                  <img src="images/icon_w3.png" />
                </li>
                <li>
                  <img src="images/icon_css.png" />
                </li>
                <li>
                  <img src="images/icon_js.png" />
                </li>
                <li>
                  <img src="images/icon_ajax.jpg" />
                </li>
              </ul>
            </div>
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
