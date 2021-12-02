<?php
namespace backend\tests;

use backend\models\Users;

class UsersTest extends \Codeception\Test\Unit
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
        $model = new Users();
    }
    public function testTableName()
    {
        $model = new Users();
        $table_name = $model->tableName();
    }
    public function testFind()
    {
        $id = 1;
        $model = Users::find($id);

    }
    public function testModel()
    {
        $model = new Users();

        $model->setusername('nomedouser');
        $this->assertTrue($model->validate(['username']));

        $model->setpasswordhash('$2y$13$GHc8szhLxD6OqYRZFVNyu.OJtnHQMgH92tXZ.51Fu5D8vQgg2YYTS');
        $this->assertTrue($model->validate(['password_hash']));

        $model->setverificationtoken('qjSDi8kOcIuECzvZGCd-5Y2K8N5vPzWX_1638456649');
        $this->assertTrue($model->validate(['verification_token']));

        $model->setpasswordresettoken('');
        $this->assertTrue($model->validate(['password_reset_token']));

        $model->setauthkey('BwSUAmTkXQ24gOuLPF1CPPxu0cLRzEWf');
        $this->assertTrue($model->validate(['auth_key']));

        $model->setstatus('10');
        $this->assertTrue($model->validate(['status']));

        $model->setupdatedat('1638456649');
        $this->assertTrue($model->validate(['updated_at']));

        $model->setcreatedat('1638456649');
        $this->assertTrue($model->validate(['created_at']));

        $model->setusertype('admin');
        $this->assertTrue($model->validate(['usertype']));

        $model->setnif('123123456');
        $this->assertTrue($model->validate(['nif']));

        $model->setbirsthday('2021-12-02');
        $this->assertTrue($model->validate(['birsthday']));

        $model->setemail('emailemail@email.pt');
        $this->assertTrue($model->validate(['email']));

        $model->setphonenumber('912000000');
        $this->assertTrue($model->validate(['phonenumber']));

        $model->setregistrationdate('2021-12-02 14:50:49');
        $this->assertTrue($model->validate(['registrationdate']));

        $model->save();
    }
}