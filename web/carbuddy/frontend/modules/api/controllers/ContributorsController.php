<?php

namespace frontend\modules\api\controllers;
use Yii;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
use common\models\User;
use yii\web\ForbiddenHttpException;

class ContributorsController extends ActiveController
{
    public $modelClass = 'backend\models\Contributors';
    const noPermission = 'Access denied';

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
        return $user;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        if (!Yii::$app->user->can('backendCrudContributor')) {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit){
        if (Yii::$app->user->can('backendCrudContributor')) {
            $Contributormodel = new $this->modelClass;
            $rec = $Contributormodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionTotal()
    {
        if (Yii::$app->user->can('backendCrudContributor')) {
            $Contributormodel = new $this->modelClass;
            $recs = $Contributormodel::find()->all();
            return ['total' => count($recs)];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }
}
