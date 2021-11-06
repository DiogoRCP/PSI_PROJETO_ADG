<?php

namespace app\modules\v1\controllers;
use app\models\User;
use yii\rest\ActiveController;
use yii\filters\auth\QueryParamAuth;
class UserController extends ActiveController
{
    public $modelClass = 'app\models\User';

    public function behaviors()
    {
        $behaviors = parent::behaviors();
        $behaviors['authenticator'] = [
            'class' => QueryParamAuth::className()
        ];

        return $behaviors;
    }
    public function auth($token) {

        $user = User::findIdentityByAccessToken($token);
        if ($user !=null)
        {
            return $user;
        } return null;
    }

    public function actionIndex()
    {
        return $this->render('index');
    }
    public function actionTotal(){
        $Usersmodel = new $this -> modelClass;
        $recs = $Usersmodel::find() -> all();
        return ['total' => count($recs)];
    }

    //http://localhost:8080/v1/user/set/3

    public function actionSet($limit){
        $Usersmodel = new $this -> modelClass;
        $rec = $Usersmodel::find() -> limit($limit) -> all();
        return ['limite' => $limit, 'Records' => $rec ];
    }

// http://localhost:8080/v1/user/post

    public function actionPost() {

        $username=\Yii::$app -> request -> get('username');
        $Usersmodel = new $this -> modelClass;
        $Usersmodel -> username = $username;

        $ret = $Usersmodel -> save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/v1/user/delete/id

    public function actionDelete($id)
    {
        $Usersmodel = new $this->modelClass;
        $ret=$Usersmodel->deleteAll("id=".$id);
        if($ret)
            return ['DelError' => $ret];
        throw new \yii\web\NotFoundHttpException("Client id not found!");
    }

    public function actionPut($id){

        $username=\Yii::$app -> request -> post('username');
        $usertype=\Yii::$app -> request -> post('usertype');
        $nif=\Yii::$app -> request -> post('nif');
        $birsthday=\Yii::$app -> request -> post('birsthday');
        $email=\Yii::$app -> request -> post('email');
        $phonenumber=\Yii::$app -> request -> post('phonenumber');

        $Usermodel = new $this->modelClass;
        $rec = $Usermodel::find()->where('id = '.$id)->one();

        $rec->username = $username;
        $rec->usertype = $usertype;
        $rec->nif = $nif;
        $rec->birsthday = $birsthday;
        $rec->email = $email;
        $rec->phonenumber = $phonenumber;

        $rec->save(false);
        return ['SaveError1' => $rec];
        //throw new \yii\web\NotFoundHttpException("Client id not found!");
    }
}
