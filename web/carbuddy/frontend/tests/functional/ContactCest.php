<?php
namespace frontend\tests\functional;
use frontend\tests\FunctionalTester;
class ContactCest
{
    public function _before(FunctionalTester $I)
    {
    }

    // tests
    public function tryToTest(FunctionalTester $I)
    {
        $I->amOnPage(['site/contact']);
        $I->submitForm('#contact-form', []);
        $I->expectTo('see validations errors');
        $I->see('Contact', 'h1');
        $I->see('Name cannot be blank');
        $I->see('Email cannot be blank');
        $I->see('Subject cannot be blank');
        $I->see('Body cannot be blank');
        $I->fillField('Name', 'erau');
        $I->fillField('E-mail', 'erau@mail.com');
        $I->fillField('Subject', 'subject');
        $I->fillField('Body', 'Message');
        $I->fillField('Verify Code', '#ADAF$$#');
    }
}
