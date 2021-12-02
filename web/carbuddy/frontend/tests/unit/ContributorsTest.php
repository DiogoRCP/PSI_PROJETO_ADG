<?php
namespace frontend\tests;

use frontend\models\Contributors;

class ContributorsTest extends \Codeception\Test\Unit
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

        $model->setspeciality('Painter');
        $this->assertTrue($model->validate(['speciality']));

        $model->setcompanyid('1');
        $this->assertTrue($model->validate(['companyid']));

        $model->setuserid('1');
        $this->assertTrue($model->validate(['userid']));

        $model->save();
    }
}