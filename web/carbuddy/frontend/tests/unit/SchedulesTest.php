<?php
namespace frontend\tests;

use frontend\models\Schedules;

class SchedulesTest extends \Codeception\Test\Unit
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
        $model = new Schedules();
    }
    public function testTableName()
    {
        $model = new Schedules();
        $table_name = $model->tableName();
    }
    public function testFind()
    {
        $id = 1;
        $model = Schedules::find($id);

    }
    public function testModel()
    {
        $model = new Schedules();

        $model->setcurrentdate('2021-11-30 17:29:35');
        $this->assertTrue($model->validate(['currentdate']));

        $model->setschedulingdate('2021-11-30 17:29:35');
        $this->assertTrue($model->validate(['schedulingdate']));

        $model->setrepairdescription('Mudança do oleo do motor');
        $this->assertTrue($model->validate(['repairdescription']));

        $model->setstate('Repaired');
        $this->assertTrue($model->validate(['state']));

        $model->setrepairstype('Maintenance');
        $this->assertTrue($model->validate(['repairtype']));

        $model->setcarid('1');
        $this->assertTrue($model->validate(['carid']));

        $model->setcompanyid('1');
        $this->assertTrue($model->validate(['companyid']));

        $model->save();
    }
}