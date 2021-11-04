<?php

namespace app\modules\v1\controllers;
use yii\rest\ActiveController;
use yii\filters\auth\HttpBasicAuth;
use app\models\User;

class ContributorsController extends ActiveController
{
    public $modelClass = 'app\models\Contributors';

    public function behaviors()
    {
        $behaviors = parent::behaviors();
        $behaviors['authenticator'] = [
            'class' => HttpBasicAuth::className(),
            'auth' => [$this, 'auth']
        ];

        return $behaviors;
    }
    public function auth($username, $password_hash) {

        $user = User::findByUsername($username);
        if ($user && $user->validatePassword($password_hash))
        {
            return $user;
        } return null;
    }

    public function actionIndex()
    {
        return $this->render('index');
    }
    public function actionTotal(){
        $Contributorssmodel = new $this -> modelClass;
        $recs = $Contributorssmodel::find() -> all();
        return ['total' => count($recs)];
    }

    //http://localhost:8080/v1/contributors/set/3

    public function actionSet($limit){
        $Contributorssmodel = new $this -> modelClass;
        $rec = $Contributorssmodel::find() -> limit($limit) -> all();
        return ['limite' => $limit, 'Records' => $rec ];
    }

// http://localhost:8080/v1/contributors/post

    public function actionPost() {

        $name=\Yii::$app -> request -> post('name');

        $Contributorssmodel = new $this -> modelClass;
        $Contributorssmodel -> name = $name;

        $ret = $Contributorssmodel -> save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/v1/contributors/delete/id

    public function actionDelete($id)
    {
        $Contributorssmodel = new $this->modelClass;
        $ret=$Contributorssmodel->deleteAll("id=".$id);
        if($ret)
            return ['DelError' => $ret];
        throw new \yii\web\NotFoundHttpException("Client id not found!");
    }
}
