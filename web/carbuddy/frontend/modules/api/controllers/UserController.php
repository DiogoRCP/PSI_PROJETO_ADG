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
    const noPermission = 'Access denied';

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
        if (\Yii::$app->user->can('backendCrudUser')) {
            return $this->render('index');
        } else {
            return self::noPermission;
        }
    }

    public function actionTotal()
    {
        if (\Yii::$app->user->can('backendCrudUser')) {
            $Usersmodel = new $this->modelClass;
            $recs = $Usersmodel::find()->all();
            return ['total' => count($recs)];
        } else {
            return self::noPermission;
        }
    }

    //http://localhost:8080/api/user/set/3

    public function actionSet($limit)
    {
        if (\Yii::$app->user->can('backendCrudUser')) {
            $Usersmodel = new $this->modelClass;
            $rec = $Usersmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        } else {
            return self::noPermission;
        }
    }

// http://localhost:8080/api/user/post

    public function actionPost()
    {
        // lembrar que para fazer post de um user, nessa altura ainda não existe access-token
        $username = \Yii::$app->request->post('username');
        $Usersmodel = new $this->modelClass;
        $Usersmodel->username = $username;

        $ret = $Usersmodel->save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/api/user/delete/id

    public function actionDelete()
    {
        if (\Yii::$app->user->can('client')) {
            $Usersmodel = new $this->modelClass;
            $ret = $Usersmodel->deleteAll("id=" . \Yii::$app->user->getId());
            if ($ret)
                return ['DelError' => $ret];
            throw new \yii\web\NotFoundHttpException("Client id not found!");
        } else {
            return self::noPermission;
        }
    }

    public function actionPut()
    {
        if (\Yii::$app->user->can('client')) {
            $user = json_decode(\Yii::$app->request->rawBody);

            $Usermodel = new $this->modelClass;
            $rec = $Usermodel::find()->where('id = ' . \Yii::$app->user->getId())->one();

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
            return self::noPermission;
        }
    }
}
