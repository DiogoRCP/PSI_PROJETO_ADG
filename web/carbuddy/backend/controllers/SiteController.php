<?php

namespace backend\controllers;

use backend\models\Companies;
use common\models\LoginForm;
use backend\models\Users;
use backend\models\Charts;
use frontend\models\Cars;
use frontend\models\Repairs;
use Yii;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;
use yii\helpers\VarDumper;
use yii\web\Controller;
use yii\web\Response;

/**
 * Site controller
 */
class SiteController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'rules' => [
                    [
                        'actions' => ['login', 'error'],
                        'allow' => true,
                    ],
                    [
                        'actions' => ['logout', 'index'],
                        'allow' => true,
                        'roles' => ['@'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'logout' => ['post'],
                ],
            ],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function actions()
    {
        return [
            'error' => [
                'class' => 'yii\web\ErrorAction',
            ],
        ];
    }

    /**
     * Displays homepage.
     *
     * @return string
     */
    public function actionIndex()
    {
        $usertypes = Users::find()->select('usertype')->distinct()->all();
        $cartypes = Cars::find()->select('cartype')->distinct()->all();
        $repairsCompany = Companies::find()->all();

        $totalRepairs = [];
        $totalRepairsCount = [];
        foreach ($repairsCompany as $comp){
            $totalRepairs[] = $comp->companyname;
            $totalRepairsCount[] = $comp->getRepairCount();
        }

        /**Aqui adiciona-se graficos novos
         * - Tipos de gráficos: line, bar, radar, polarArea, pie, doughnut
         **/
        $charts = [
            new Charts("pie", "User Types", Charts::LabelAndData($usertypes, "user"), true),
            new Charts("pie", "Car Types", Charts::LabelAndData($cartypes, "car"), true),
            new Charts("pie", "Repairs per Companies", ['data' => $totalRepairs, 'values' => $totalRepairsCount], true)
        ];

        if (Yii::$app->user->can('admin')) {
            return $this->render('index', [
                    'charts' => $charts]
            );
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Login action.
     *
     * @return string|Response
     */
    public
    function actionLogin()
    {
        if (!Yii::$app->user->isGuest) {
            return $this->goHome();
        }

        $this->layout = 'blank';
        $model = new LoginForm();
        if ($model->load(Yii::$app->request->post()) && $model->login()) {
            return $this->goBack();
        }

        $model->password = '';

        return $this->render('login', [
            'model' => $model,
        ]);
    }

    /**
     * Logout action.
     *
     * @return Response
     */
    public
    function actionLogout()
    {
        Yii::$app->user->logout();

        return $this->goHome();
    }
}
