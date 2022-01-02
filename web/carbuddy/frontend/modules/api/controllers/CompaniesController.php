<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use Yii;
use yii\filters\auth\QueryParamAuth;
use yii\helpers\VarDumper;
use yii\rest\ActiveController;
use yii\web\ForbiddenHttpException;

class CompaniesController extends ActiveController
{
    public $modelClass = 'backend\models\Companies';
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
        return $user;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        if (!Yii::$app->user->can('backendCrudCompany')) {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionTotal()
    {
        if (Yii::$app->user->can('backendCrudCompany')) {
            $Companiessmodel = new $this->modelClass;
            $recs = $Companiessmodel::find()->all();
            return ['total' => count($recs)];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }


    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit)
    {
        if (Yii::$app->user->can('backendCrudCompany')) {
            $Companiessmodel = new $this->modelClass;
            $rec = $Companiessmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }
}
