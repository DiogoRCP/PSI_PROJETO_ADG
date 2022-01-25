<?php

namespace frontend\modules\api\controllers;

use frontend\models\Users;
use Yii;
use yii\base\Exception;
use yii\rest\ActiveController;
use yii\web\ConflictHttpException;

class SignupController extends ActiveController
{
    public $modelClass = 'frontend\models\SignupForm';

    public function actionPost()
    {
        $user = Yii::$app->request->post();

        $signupmodel = new $this->modelClass;

        if (Users::find()->where('username = "' . $user['username'] . '"')->one())
            throw new ConflictHttpException("Username already in use", 0);

        if (Users::find()->where('nif = "' . $user['nif'] . '"')->one())
            throw new ConflictHttpException("Nif already in use", 1);

        if (Users::find()->where('email = "' . $user['email'] . '"')->one())
            throw new ConflictHttpException("Email already in use", 2);

        if (Users::find()->where('phonenumber = "' . $user['phonenumber'] . '"')->one())
            throw new ConflictHttpException("Email already in use", 3);

        $signupmodel->username = $user['username'];
        $signupmodel->password = $user['password'];
        $signupmodel->nif = $user['nif'];
        $signupmodel->email = $user['email'];
        $signupmodel->phonenumber = $user['phonenumber'];
        $signupmodel->birsthday = $user['birsthday'];

        $ret = $signupmodel->signup();
        if ($ret) $signupmodel->AssignUser();
        return ['Save' => $ret];
    }

}
