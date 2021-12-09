<?php

namespace frontend\tests\acceptance;

use frontend\tests\AcceptanceTester;
use Yii;
use yii\helpers\Url;

class AcceptanceCest
{
    //Para correr estes testes
    //php yii serve 127.0.0.1:8000 --docroot="frontend/web"

    public function checkHome(AcceptanceTester $I)
    {
        $I->amOnPage(Url::toRoute('/site/index'));
        $I->see('Carbuddy');
    }

    public function checkSignup(AcceptanceTester $I)
    {
        $I->amOnPage(Url::toRoute('/site/index'));
        $I->see('Carbuddy');
        $I->seeLink('Signup');
        $I->click('Signup');
        $I->see('Sign up', 'h2');
    }

    public function checkLogin(AcceptanceTester $I)
    {
        $I->amOnPage(Url::toRoute('/site/index'));
        $I->see('Carbuddy');
        $I->seeLink('Login');
        $I->click('Login');
        $I->see('Username', 'label');
        $I->see('Password', 'label');
    }

    public function checkAbout(AcceptanceTester $I)
    {
        $I->amOnPage(Url::toRoute('/site/about'));
        $I->see('Carbuddy');
        $I->see('About', 'h1');
        $I->see('GitHub and Jira', 'h2');
        $I->see('Development of a website for product management by the application manager and mechanics.
                    Development of an Android application for use of the product by customers and mechanics.
                    Development of a database to store all the information contained both on the website and in the Android application.
                    Development of an API to connect a database to the website and Android application.', 'p');
        $I->see('Goals', 'h2');
        $I->see('To manage the project, we use Jira and GitHub.', 'p');
    }

    //Para este teste não dar erro
    //É necessário a tabela user da BD estar vazia
    public function doSignup(AcceptanceTester $I)
    {
        $I->amOnPage(Url::toRoute('/site/signup'));
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->fillField('E-mail', 'teste@teste.pt');
        $I->fillField('NIF', '123123000');
        $I->fillField('Birthday', '2021-12-06');
        $I->fillField('Phone Number', '911000111');
        $I->click('signup-button');
    }

    public function doLogin(AcceptanceTester $I)
    {
        $I->amOnPage(Url::toRoute('/site/login'));
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->click('login-button');
    }

    public function doLogout(AcceptanceTester $I)
    {
        $I->amOnPage(Url::toRoute('/site/login'));
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->click('login-button');
        $_SESSION = array();
        $_GET     = array();
        $_POST    = array();
        $_COOKIE  = array();
        $_REQUEST = array();
        Yii::$app->user->logout();
        $I->amOnPage(Url::toRoute('/site/about'));
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
