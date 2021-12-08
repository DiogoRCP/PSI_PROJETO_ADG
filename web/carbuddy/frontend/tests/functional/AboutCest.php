<?php
namespace frontend\tests\functional;
use frontend\tests\FunctionalTester;
class AboutCest
{
    public function _before(FunctionalTester $I)
    {
    }

    // tests
    public function aboutWithoutLogin(FunctionalTester $I)
    {
        $I->amOnPage(['site/about']);
        $I->see('About', 'h1');
        $I->see('GitHub and Jira', 'h2');
        $I->see('Development of a website for product management by the application manager and mechanics.
                    Development of an Android application for use of the product by customers and mechanics.
                    Development of a database to store all the information contained both on the website and in the Android application.
                    Development of an API to connect a database to the website and Android application.', 'p');
        $I->see('Goals', 'h2');
        $I->see('To manage the project, we use Jira and GitHub.', 'p');
    }

    public function aboutWithLogin(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->click('login-button');
        $I->amOnPage(['site/about']);
        $I->see('About', 'h1');
        $I->see('GitHub and Jira', 'h2');
        $I->see('Development of a website for product management by the application manager and mechanics.
                    Development of an Android application for use of the product by customers and mechanics.
                    Development of a database to store all the information contained both on the website and in the Android application.
                    Development of an API to connect a database to the website and Android application.', 'p');
        $I->see('Goals', 'h2');
        $I->see('To manage the project, we use Jira and GitHub.', 'p');
    }
}
