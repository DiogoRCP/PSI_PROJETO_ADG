<?php
namespace backend\tests;

use backend\models\Companies;
use Yii;



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
        $model = new Companies();
        $post = ["Midas", "Midas@midas.pt",  "912123049", "123123053"];
        $this->assertTrue($model->load($post), 'Load POST data');
    }
}