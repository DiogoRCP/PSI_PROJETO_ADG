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

        $model->setvin('');
        $this->assertFalse($model->validate(['vin']));

        $model->setvin('312321423451235253');
        $this->assertTrue($model->validate(['vin']));

        $model->setbrand('');
        $this->assertFalse($model->validate(['brand']));

        $model->setbrand('bmw');
        $this->assertTrue($model->validate(['brand']));

        $model->setmodel('');
        $this->assertFalse($model->validate(['model']));

        $model->setmodel('120d');
        $this->assertTrue($model->validate(['model']));

        $model->setcolor('');
        $this->assertFalse($model->validate(['color']));

        $model->setcolor('#1323124');
        $this->assertTrue($model->validate(['color']));

        //o car type recebe os dados da dropdownlist
        $model->setcartype('Passengercar');
        $this->assertTrue($model->validate(['cartype']));

        $model->setdisplacement('1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
        $this->assertFalse($model->validate(['displacement']));

        $model->setdisplacement('1500');
        $this->assertTrue($model->validate(['displacement']));

        //Vem da dropdownlist
        $model->setfueltype('gasoline');
        $this->assertTrue($model->validate(['fueltype']));

        $model->setregistration('AA-00-aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaAB');
        $this->assertFalse($model->validate(['registration']));

        $model->setregistration('AA-00-AB');
        $this->assertTrue($model->validate(['registration']));

        $model->setmodelyear('');
        $this->assertFalse($model->validate(['modelyear']));

        $model->setmodelyear('2019');
        $this->assertTrue($model->validate(['modelyear']));

        $model->setkilometers('');
        $this->assertFalse($model->validate(['kilometers']));

        $model->setkilometers('1234');
        $this->assertTrue($model->validate(['kilometers']));

        $model->setstate('');
        $this->assertFalse($model->validate(['state']));

        $model->setstate('pending');
        $this->assertTrue($model->validate(['state']));

        $model->setuserid('');
        $this->assertTrue($model->validate(['userid']));

        $model->setuserid('1');
        $this->assertTrue($model->validate(['userid']));

        $model->save();

        //Este teste varia consoante os dados da base de dados
        $this->tester->seeInDatabase('Cars', ['vin'=>'1J4BA3H10AL171412']);
    }
}