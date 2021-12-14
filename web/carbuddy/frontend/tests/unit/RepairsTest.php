<?php
namespace frontend\tests;

use frontend\models\Repairs;

class RepairsTest extends \Codeception\Test\Unit
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
        $model = new Repairs();
    }
    public function testTableName()
    {
        $model = new Repairs();
        $table_name = $model->tableName();
    }
    public function testFind()
    {
        $id = 1;
        $model = Repairs::find($id);

    }
    public function testModel()
    {
        $model = new Repairs();

        $model->setkilometers('sadfgfsdg');
        $this->assertFalse($model->validate(['kilometers']));

        $model->setkilometers('123123');
        $this->assertTrue($model->validate(['kilometers']));

        //Data automática da base de dados, não testar false
        $model->setrepairdate('2021-11-30 17:29:35');
        $this->assertTrue($model->validate(['repairdate']));

        $model->setrepairdescription('Mudança do oleo do moto1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111r');
        $this->assertFalse($model->validate(['repairdescription']));

        $model->setrepairdescription('Mudança do oleo do motor');
        $this->assertTrue($model->validate(['repairdescription']));

        $model->setstate('Repaired111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111');
        $this->assertFalse($model->validate(['state']));

        $model->setstate('Repaired');
        $this->assertTrue($model->validate(['state']));

        $model->setrepairstype('Maintenanc111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111e');
        $this->assertFalse($model->validate(['repairtype']));

        $model->setrepairstype('Maintenance');
        $this->assertTrue($model->validate(['repairtype']));

        $model->setcarid('0');
        $this->assertTrue($model->validate(['carid']));

        $model->setcarid('1');
        $this->assertTrue($model->validate(['carid']));

        $model->setcontributorid('0');
        $this->assertTrue($model->validate(['contributorid']));

        $model->setcontributorid('1');
        $this->assertTrue($model->validate(['contributorid']));

        $model->save();
    }
}