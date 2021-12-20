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
        if ($user != null) {
            return $user;
        }
        return null;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        if ($action === 'index') {
            if (!Yii::$app->user->can('client')) {
                throw new ForbiddenHttpException(self::noPermission);
            }
        } else {
            if (!Yii::$app->user->can('backendCrudCompany')) {
                throw new ForbiddenHttpException(self::noPermission);
            }
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
        } else {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    //http://localhost:8080/v1/companies/set/3

    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit)
    {
        if (Yii::$app->user->can('backendCrudCompany')) {
            $Companiessmodel = new $this->modelClass;
            $rec = $Companiessmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        } else {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }
}
