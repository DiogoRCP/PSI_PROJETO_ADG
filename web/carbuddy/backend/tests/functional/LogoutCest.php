<?php
namespace backend\tests\functional;
use backend\tests\FunctionalTester;
use Yii;

class LogoutCest
{
    public function _before(FunctionalTester $I)
    {
    }

    // tests
    public function logoutUser(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->click('login-button');
        $_SESSION = array();
        $_GET     = array();
        $_POST    = array();
        $_COOKIE  = array();
        $_REQUEST = array();
        Yii::$app->user->logout();
        $I->amOnPage(['site/login']);
        $I->see('Username', 'label');
        $I->see('Password', 'label');
    }
}
