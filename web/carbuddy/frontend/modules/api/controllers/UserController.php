<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use frontend\models\Users;
use Yii;
use yii\rest\ActiveController;
use yii\filters\auth\QueryParamAuth;
use yii\web\ConflictHttpException;
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
        return $user;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionAccount()
    {
        if (!Yii::$app->user->isGuest) {
            $Usersmodel = new $this->modelClass;
            $user = $Usersmodel::findOne(Yii::$app->user->getId());

            return $user;
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionTotal()
    {
        if (Yii::$app->user->can('backendCrudUser')) {
            $Usersmodel = new $this->modelClass;
            $recs = $Usersmodel::find()->all();
            return ['total' => count($recs)];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }


    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit)
    {
        if (Yii::$app->user->can('backendCrudUser')) {
            $Usersmodel = new $this->modelClass;
            $rec = $Usersmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }


    /**
     * @throws ForbiddenHttpException
     */
    public function actionDeleted()
    {
        if (Yii::$app->user->can('client')) {
            $Usersmodel = new $this->modelClass;
            $ret = $Usersmodel->deleteAll("id=" . Yii::$app->user->getId());
            return ['Del' => $ret];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws \yii\base\Exception
     * @throws ForbiddenHttpException
     */
    public function actionPut()
    {
        if (Yii::$app->user->can('client')) {
            $user = Yii::$app->request->post();

            $Usermodel = new $this->modelClass;
            $rec = $Usermodel::find()->where('id = ' . Yii::$app->user->getId())->one();

            if(isset($user['email'])){
                if ($user['email'] != $rec->email && Users::find()->where('email = "' . $user['email'] . '"')->one())
                    throw new ConflictHttpException("Email is already in use", 0);
            }

            if(isset($user['email']))$rec->email = $user['email'];
            if(isset($user['password']))$rec->password_hash = Yii::$app->security->generatePasswordHash($user['password']);
            $rec->save();
            return ['Save' => $rec];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }
}
