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

        //Não se faz test falso porque este dado é automático na bd
        $model->setcurrentdate('2021-11-30 17:29:35');
        $this->assertTrue($model->validate(['currentdate']));

        //Data automática
        $model->setschedulingdate('2021-11-30 17:29:35');
        $this->assertTrue($model->validate(['schedulingdate']));

        $model->setrepairdescription('Mudança1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111 do oleo do motor');
        $this->assertFalse($model->validate(['repairdescription']));

        $model->setrepairdescription('Mudança do oleo do motor');
        $this->assertTrue($model->validate(['repairdescription']));

        $model->setstate('Repaired11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111');
        $this->assertFalse($model->validate(['state']));

        $model->setstate('Repaired');
        $this->assertTrue($model->validate(['state']));

        $model->setrepairstype('Maintenance1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111');
        $this->assertFalse($model->validate(['repairtype']));

        $model->setrepairstype('Maintenance');
        $this->assertTrue($model->validate(['repairtype']));

        //No schedule o carro é associada automaticamente
        $model->setcarid('1');
        $this->assertTrue($model->validate(['carid']));

        //No schedule a empresa é associada automaticamente
        $model->setcompanyid('1');
        $this->assertTrue($model->validate(['companyid']));

        $model->save();
    }
}