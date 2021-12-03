<?php
namespace backend\tests;

use backend\models\Companies;

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
    public function testModel()
    {
        $model = new Companies();

        $model->setname('NomeEmpresaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
        $this->assertFalse($model->validate(['companyname']));

        $model->setname('NomeEmpresa');
        $this->assertTrue($model->validate(['companyname']));

        $model->setemail('nomeempresa@n1111111111111111111111111111111111111111111111111111111111111111111111111111111111ome.pt');
        $this->assertFalse($model->validate(['email']));

        $model->setemail('nomeempresa@nome.pt');
        $this->assertTrue($model->validate(['email']));

        $model->setnif('9129120113');
        $this->assertFalse($model->validate(['nif']));

        $model->setnif('912912013');
        $this->assertTrue($model->validate(['nif']));

        $model->setphonenumber('9129120114444444444444444444444444444444444444444444444444444444443');
        $this->assertFalse($model->validate(['phonenumber']));

        $model->setphonenumber('912912013');
        $this->assertTrue($model->validate(['phonenumber']));

        //Data automatica nd bd
        $model->setreistrationdate('2021-11-30 17:19:07');
        $this->assertTrue($model->validate(['registrationdate']));

        $model->save();
    }
}