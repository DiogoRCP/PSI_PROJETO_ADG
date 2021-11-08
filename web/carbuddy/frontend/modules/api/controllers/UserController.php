<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use PHPUnit\Util\Log\JSON;
use yii\helpers\VarDumper;
use yii\rest\ActiveController;
use yii\filters\auth\QueryParamAuth;

class UserController extends ActiveController
{
    public $modelClass = 'common\models\User';

    public function behaviors()
    {
        $behaviors = parent::behaviors();
        $behaviors['authenticator'] = [
            'class' => QueryParamAuth::className()
        ];

        return $behaviors;
    }

    public function auth($token)
    {

        $user = User::findIdentityByAccessToken($token);
        if ($user != null) {
            return $user;
        }
        return null;
    }

    public function actionIndex()
    {
        return $this->render('index');
    }

    public function actionTotal()
    {
        $Usersmodel = new $this->modelClass;
        $recs = $Usersmodel::find()->all();
        return ['total' => count($recs)];
    }

    //http://localhost:8080/api/user/set/3

    public function actionSet($limit)
    {
        $Usersmodel = new $this->modelClass;
        $rec = $Usersmodel::find()->limit($limit)->all();
        return ['limite' => $limit, 'Records' => $rec];
    }

// http://localhost:8080/api/user/post

    public function actionPost()
    {

        $username = \Yii::$app->request->post('username');
        $Usersmodel = new $this->modelClass;
        $Usersmodel->username = $username;

        $ret = $Usersmodel->save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/api/user/delete/id

    public function actionDelete($id)
    {
        $Usersmodel = new $this->modelClass;
        $ret = $Usersmodel->deleteAll("id=" . $id);
        if ($ret)
            return ['DelError' => $ret];
        throw new \yii\web\NotFoundHttpException("Client id not found!");
    }

    public function actionPut($id)
    {
        if (\Yii::$app->user->getId() == $id) {
            $user = json_decode(\Yii::$app->request->rawBody);

            $Usermodel = new $this->modelClass;
            $rec = $Usermodel::find()->where('id = ' . $id)->one();

            if (isset($user->username)) $rec->username = $user->username;
            if (isset($user->usertype)) $rec->usertype = $user->usertype;
            if (isset($user->nif)) $rec->nif = $user->nif;
            if (isset($user->birsthday)) $rec->birsthday = $user->birsthday;
            if (isset($user->email)) $rec->email = $user->email;
            if (isset($user->phonenumber)) $rec->phonenumber = $user->phonenumber;

            $rec->save(false);
            //return ['SaveError1' => $rec];
            //throw new \yii\web\NotFoundHttpException("Client id not found!");
        } else {
            return "Sem permissões";
        }
    }
}
