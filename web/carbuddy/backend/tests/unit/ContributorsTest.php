<?php
namespace backend\tests;

use backend\models\Contributors;

class ContributorsTest extends \Codeception\Test\Unit
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
        $model = new Contributors();
    }
    public function testTableName()
    {
        $model = new Contributors();
        $table_name = $model->tableName();
    }
    public function testFind()
    {
        $id = 1;
        $model = Contributors::find($id);

    }
    public function testModel()
    {
        $model = new Contributors();

        $model->setspeciality('');
        $this->assertFalse($model->validate(['speciality']));

        $model->setspeciality('Painter');
        $this->assertTrue($model->validate(['speciality']));

        //Dado que vem da dropdownlist
        $model->setcompanyid('1');
        $this->assertTrue($model->validate(['companyid']));

        //Dado que vem da dropdownlist
        $model->setuserid('1');
        $this->assertTrue($model->validate(['userid']));

        $model->save();

        $this->tester->seeInDatabase('Contributors', ['speciality'=>'Mechanical', 'companyId'=>'1', 'userId'=>'14']);
    }
}