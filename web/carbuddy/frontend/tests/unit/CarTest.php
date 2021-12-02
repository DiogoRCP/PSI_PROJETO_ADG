<?php
namespace frontend\tests;

use frontend\models\Cars;

class CarTest extends \Codeception\Test\Unit
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
        $model = new Cars();
    }
    public function testTableName()
    {
        $model = new Cars();
        $table_name = $model->tableName();
    }
    public function testFind()
    {
        $id = 1;
        $model = Cars::find($id);

    }
    public function testModel()
    {
        $model = new Cars();

        $model->setvin('312321423451235253');
        $this->assertTrue($model->validate(['vin']));

        $model->setbrand('bmw');
        $this->assertTrue($model->validate(['brand']));

        $model->setmodel('120d');
        $this->assertTrue($model->validate(['model']));

        $model->setcolor('#1323124');
        $this->assertTrue($model->validate(['color']));

        $model->setcartype('Passengercar');
        $this->assertTrue($model->validate(['cartype']));

        $model->setdisplacement('1500');
        $this->assertTrue($model->validate(['displacement']));

        $model->setfueltype('gasoline');
        $this->assertTrue($model->validate(['fueltype']));

        $model->setregistration('AA-00-AB');
        $this->assertTrue($model->validate(['registration']));

        $model->setmodelyear('2019');
        $this->assertTrue($model->validate(['modelyear']));

        $model->setkilometers('1234');
        $this->assertTrue($model->validate(['kilometers']));

        $model->setstate('pending');
        $this->assertTrue($model->validate(['state']));

        $model->setuserid('1');
        $this->assertTrue($model->validate(['userid']));

        $model->save();
    }
}