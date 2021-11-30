<?php
namespace backend\tests;

use backend\models\Companies;
use backend\controllers\CompanyController;
use Yii;
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
    public function testCreateModel()
    {
        $model = new Companies();
    }
    public function testTableName()
    {
        $model = new Companies();
        $table_name = $model->tableName();
    }
    public function testFind()
    {
        $id = 1;
        $model = Companies::find($id);

    }
    public function testCreateModelWithData()
    {
        Companies::getDb();
        $model = new Companies();
        $model->companyname = "Midas";
        $model->email = "Midas@midas.pt";
        $model->phonenumber = "912123049";
        $model->nif = "123123053";
        $model->save();
        expect('model should blow', $model->save());
    }
}