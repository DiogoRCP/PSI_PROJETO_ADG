<?php

namespace frontend\controllers;

use frontend\models\Cars;
use frontend\models\Companies;
use frontend\models\Schedules;
use frontend\models\SchedulesSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * SchedulesController implements the CRUD actions for Schedules model.
 */
class SchedulesController extends Controller
{
    /**
     * @inheritDoc
     */
    public function behaviors()
    {
        return array_merge(
            parent::behaviors(),
            [
                'verbs' => [
                    'class' => VerbFilter::className(),
                    'actions' => [
                        'delete' => ['POST'],
                    ],
                ],
            ]
        );
    }

    /**
     * Lists all Schedules models.
     * @return mixed
     */
    public function actionIndex()
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
        $searchModel = new SchedulesSearch();
        $dataProvider = $searchModel->search($this->request->queryParams);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Displays a single Schedules model.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Creates a new Schedules model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
        $model = new Schedules();
        $modelCompanies = Companies::find()->all();
        $modelCars = Cars::find()->all();

        if ($this->request->isPost) {
            if ($model->load($this->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }
        } else {
            $model->loadDefaultValues();
        }

        return $this->render('create', [
            'model' => $model, 'modelCompanies' => $modelCompanies, 'modelCars' => $modelCars,
        ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Updates an existing Schedules model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
        $model = $this->findModel($id);
        $modelCompanies = Companies::find()->all();
        $modelCars = Cars::find()->all();

        if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->id]);
        }

        return $this->render('update', [
            'model' => $model, 'modelCompanies' => $modelCompanies, 'modelCars' => $modelCars,
        ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Deletes an existing Schedules model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
        $this->findModel($id)->delete();

        return $this->redirect(['index']);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Finds the Schedules model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Schedules the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Schedules::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
