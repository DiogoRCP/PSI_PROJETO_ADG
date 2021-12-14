<?php
namespace frontend\tests\functional;
use frontend\tests\FunctionalTester;
class SignupCest
{
    public function _before(FunctionalTester $I)
    {
    }

    // tests

    /**
     * @param FunctionalTester $I
     */
    //Para este teste não dar erro
    //É necessário a tabela user da BD estar vazia
    public function signupUser(FunctionalTester $I)
    {
        $I->amOnPage('/site/signup');
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->fillField('E-mail', 'teste@teste.pt');
        $I->fillField('NIF', '123123000');
        $I->fillField('Birthday', '2021-12-06');
        $I->fillField('Phone Number', '911000111');
        $I->click('signup-button');
    }
}
