<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use Yii;
use yii\rest\ActiveController;
use yii\filters\auth\QueryParamAuth;
use yii\web\ForbiddenHttpException;
use yii\web\NotFoundHttpException;

class UserController extends ActiveController
{
    public $modelClass = 'common\models\User';
    const noPermission = 'Access denied - Use Custom Methods';

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

    public function checkAccess($action, $model = null, $params = [])
    {
        throw new ForbiddenHttpException(self::noPermission);
    }

    public function actionAccount()
    {
        if (!Yii::$app->user->isGuest) {
            $Usersmodel = new $this->modelClass;
            $user = $Usersmodel::findOne(Yii::$app->user->getId());

            return $user;
        } else {
            return self::noPermission;
        }
    }

    public function actionTotal()
    {
        if (Yii::$app->user->can('backendCrudUser')) {
            $Usersmodel = new $this->modelClass;
            $recs = $Usersmodel::find()->all();
            return ['total' => count($recs)];
        } else {
            return self::noPermission;
        }
    }


    public function actionSet($limit)
    {
        if (Yii::$app->user->can('backendCrudUser')) {
            $Usersmodel = new $this->modelClass;
            $rec = $Usersmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        } else {
            return self::noPermission;
        }
    }



    public function actionDeleted()
    {
        if (Yii::$app->user->can('client')) {
            $Usersmodel = new $this->modelClass;
            $ret = $Usersmodel->deleteAll("id=" . Yii::$app->user->getId());
            return ['Del' => $ret];
        } else {
            return self::noPermission;
        }
    }

    public function actionPut()
    {
        if (Yii::$app->user->can('client')) {
            $user = Yii::$app->request->post();

            $Usermodel = new $this->modelClass;
            $rec = $Usermodel::find()->where('id = ' . Yii::$app->user->getId())->one();

            $rec->email = $user['email'];
            $rec->password_hash = Yii::$app->security->generatePasswordHash($user['password']);
            $rec->save();
            return ['Save' => $rec];
        } else {
            return self::noPermission;
        }
    }
}
