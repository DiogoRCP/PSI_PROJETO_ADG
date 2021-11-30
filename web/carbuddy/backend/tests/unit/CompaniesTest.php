<?php
namespace backend\tests;

use backend\models\Companies;
use backend\controllers\CompanyController;
use yii\caching\DummyCache;


class CompaniesTest extends \Codeception\Test\Unit
{
    /**
     * @var \backend\tests\UnitTester
     */
    protected $tester;
    
    protected function _before()
    {
    }

    protected function _after()
    {
    }

    // tests
    public function testSomeFeature()
    {
        //Testes ao modelo Companies
        $id = 1;
        $model = new Companies();

        $model->companyname = "Midas";
        $model->phonenumber = "912123065";
        $model->nif = "123412341";
        $model->email = "Midas@hotmail.com";

        expect('model should blow', $model->save());
    }
}