<?php
namespace frontend\tests;

use frontend\models\Companies;

class CompaniesTest extends \Codeception\Test\Unit
{
    /**
     * @var \frontend\tests\UnitTester
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
    public function testModel()
    {
        $model = new Companies();

        $model->setname('NomeEmpresa');
        $this->assertTrue($model->validate(['companyname']));

        $model->setemail('nomeempresa@nome.pt');
        $this->assertTrue($model->validate(['email']));

        $model->setnif('912912013');
        $this->assertTrue($model->validate(['nif']));

        $model->setphonenumber('912912013');
        $this->assertTrue($model->validate(['phonenumber']));

        $model->setreistrationdate('2021-11-30 17:19:07');
        $this->assertTrue($model->validate(['registrationdate']));

        $model->save();
    }
}